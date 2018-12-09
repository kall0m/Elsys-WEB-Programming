package org.elsys.netprog.rest.factories;

import org.elsys.netprog.rest.models.CowsAndBulls;

public class CowsAndBullsFactory {
	public final static CowsAndBulls createCowsAndBulls(String id) {
		return new CowsAndBulls(id);
	}
}
