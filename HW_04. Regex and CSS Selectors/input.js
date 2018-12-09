regExps = {
"exercise_1": /[A-Z][a-z]+/,
"exercise_2": /088[1-9]\d{6}/,
"exercise_3": /[^01]+/,
"exercise_4": /^[a-zA-Z][\w.]{2,30}$/,
"exercise_5": /^(\d|[1-9]\d|[1-9]\d{2}|1[0-4]\d{2}|1500)$/,
"exercise_6": /class=['"].+['"]/
};
cssSelectors = {
"exercise_1": "css > item:last-of-type > java:first-of-type",
"exercise_2": "css > different java",
"exercise_3": "css > item:last-of-type tag",
"exercise_4": "css > item:last-of-type",
"exercise_5": "css > item:first-of-type java + .class1",
"exercise_6": "#someId .class1 .class2, #someId .class2 .class1",
"exercise_7": "#diffId2 java:last-of-type",
"exercise_8": "#someId"
};
