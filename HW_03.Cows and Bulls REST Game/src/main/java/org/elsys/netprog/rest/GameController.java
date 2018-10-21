package org.elsys.netprog.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.module.SimpleModule;
import org.elsys.netprog.rest.factories.CowsAndBullsFactory;
import org.elsys.netprog.rest.models.CowsAndBulls;
import org.elsys.netprog.rest.serializers.CowsAndBullsSerializer;


@Path("/game")
public class GameController {
	private static Map<String, CowsAndBulls> games = new HashMap<String, CowsAndBulls>();
	private static ObjectMapper mapper = new ObjectMapper();
	private static SimpleModule module = new SimpleModule("CowsAndBulls Serializer", new Version(0, 1, 1, "FINAL"));
	
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException {
		String id = UUID.randomUUID().toString();
		
		while (games.containsKey(id)) {
			id = UUID.randomUUID().toString();
		}
		
		CowsAndBulls newGame = CowsAndBullsFactory.createCowsAndBulls(id);
		
		games.put(id, newGame);
		
		return Response.created(new URI("/games")).entity(id).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception {
		CowsAndBulls game = games.get(gameId);
		
		if (game == null) {
			return Response.status(404).build();
		}
		
		if (game.getSuccess()) {
			return Response.ok().entity(game).build();
		}
		
		game.reset();
		
		Integer number = null;
		
		try {
			number = Integer.parseInt(guess);
		} catch (Exception error) {
			return Response.status(400).build();
		}
		
		if (!CowsAndBulls.isGuessingNumberValid(number)) {
			return Response.status(400).build();
		}
		
		game.play(number);
		
		return Response.ok().entity(game).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() throws JsonGenerationException, JsonMappingException, IOException {
		module.addSerializer(CowsAndBulls.class, new CowsAndBullsSerializer());
		
		mapper.registerModule(module);
		
		String serialized = mapper.writeValueAsString(games.values());
		
		return Response.ok().entity(serialized).build();
	}
}
