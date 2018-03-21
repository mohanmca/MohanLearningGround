0	:	1.1 Primitives: When you access a primitive type you work directly on its value.
1	:	1.2 Complex: When you access a complex type you work on a reference to its value.
2	:	2.1 Use const for all of your references; avoid using var. eslint: prefer-const, no-const-assign
3	:	2.2 If you must reassign references, use let instead of var. eslint: no-var jscs: disallowVar
4	:	2.3 Note that both let and const are block-scoped.
5	:	3.1 Use the literal syntax for object creation. eslint: no-new-object
6	:	3.2 Use computed property names when creating objects with dynamic property names.
7	:	3.3 Use object method shorthand. eslint: object-shorthand jscs: requireEnhancedObjectLiterals
8	:	3.4 Use property value shorthand. eslint: object-shorthand jscs: requireEnhancedObjectLiterals
9	:	3.5 Group your shorthand properties at the beginning of your object declaration.
10	:	3.6 Only quote properties that are invalid identifiers. eslint: quote-props jscs: disallowQuotedKeysInObjects
11	:	3.7 Do not call Object.prototype methods directly, such as hasOwnProperty, propertyIsEnumerable, and isPrototypeOf.
12	:	3.8 Prefer the object spread operator over Object.assign to shallow-copy objects. Use the object rest operator to get a new object with certain properties omitted.
13	:	4.1 Use the literal syntax for array creation. eslint: no-array-constructor
14	:	4.2 Use Array#push instead of direct assignment to add items to an array.
15	:	4.3 Use array spreads ... to copy arrays.
16	:	4.4 To convert an array-like object to an array, use spreads ... instead of Array.from.
17	:	4.5 Use Array.from instead of spread ... for mapping over iterables, because it avoids creating an intermediate array.
18	:	4.6 Use return statements in array method callbacks. It’s ok to omit the return if the function body consists of a single statement returning an expression without side effects, following 8.2. eslint: array-callback-return
19	:	4.7 Use line breaks after open and before close array brackets if an array has multiple lines
20	:	5.1 Use object destructuring when accessing and using multiple properties of an object. eslint: prefer-destructuring jscs: requireObjectDestructuring
21	:	5.2 Use array destructuring. eslint: prefer-destructuring jscs: requireArrayDestructuring
22	:	5.3 Use object destructuring for multiple return values, not array destructuring. jscs: disallowArrayDestructuringReturn
23	:	6.1 Use single quotes '' for strings. eslint: quotes jscs: validateQuoteMarks
24	:	6.2 Strings that cause the line to go over 100 characters should not be written across multiple lines using string concatenation.
25	:	6.3 When programmatically building up strings, use template strings instead of concatenation. eslint: prefer-template template-curly-spacing jscs: requireTemplateStrings
26	:	6.5 Do not unnecessarily escape characters in strings. eslint: no-useless-escape
27	:	7.1 Use named function expressions instead of function declarations. eslint: func-style jscs: disallowFunctionDeclarations
28	:	7.2 Wrap immediately invoked function expressions in parentheses. eslint: wrap-iife jscs: requireParenthesesAroundIIFE
29	:	7.4 Note: ECMA-262 defines a block as a list of statements. A function declaration is not a statement.
30	:	7.5 Never name a parameter arguments. This will take precedence over the arguments object that is given to every function scope.
31	:	7.6 Never use arguments, opt to use rest syntax ... instead. eslint: prefer-rest-params
32	:	7.7 Use default parameter syntax rather than mutating function arguments.
33	:	7.8 Avoid side effects with default parameters.
34	:	7.9 Always put default parameters last.
35	:	7.10 Never use the Function constructor to create a new function. eslint: no-new-func
36	:	7.11 Spacing in a function signature. eslint: space-before-function-paren space-before-blocks
37	:	7.12 Never mutate parameters. eslint: no-param-reassign
38	:	7.13 Never reassign parameters. eslint: no-param-reassign
39	:	7.14 Prefer the use of the spread operator ... to call variadic functions. eslint: prefer-spread
40	:	7.15 Functions with multiline signatures, or invocations, should be indented just like every other multiline list in this guide: with each item on a line by itself, with a trailing comma on the last item. eslint: function-paren-newline
41	:	8.1 When you must use an anonymous function (as when passing an inline callback), use arrow function notation. eslint: prefer-arrow-callback, arrow-spacing jscs: requireArrowFunctions
42	:	8.2 If the function body consists of a single statement returning an expression without side effects, omit the braces and use the implicit return. Otherwise, keep the braces and use a return statement. eslint: arrow-parens, arrow-body-style jscs: disallowParenthesesAroundArrowParam, requireShorthandArrowFunctions
43	:	8.3 In case the expression spans over multiple lines, wrap it in parentheses for better readability.
44	:	8.4 If your function takes a single argument and doesn’t use braces, omit the parentheses. Otherwise, always include parentheses around arguments for clarity and consistency. Note: it is also acceptable to always use parentheses, in which case use the “always” option for eslint or do not include disallowParenthesesAroundArrowParam for jscs. eslint: arrow-parens jscs: disallowParenthesesAroundArrowParam
45	:	8.5 Avoid confusing arrow function syntax (=>) with comparison operators (<=, >=). eslint: no-confusing-arrow
46	:	9.1 Always use class. Avoid manipulating prototype directly.
47	:	9.2 Use extends for inheritance.
48	:	9.3 Methods can return this to help with method chaining.
49	:	9.4 It’s okay to write a custom toString() method, just make sure it works successfully and causes no side effects.
50	:	9.5 Classes have a default constructor if one is not specified. An empty constructor function or one that just delegates to a parent class is unnecessary. eslint: no-useless-constructor
51	:	9.6 Avoid duplicate class members. eslint: no-dupe-class-members
52	:	10.1 Always use modules (import/export) over a non-standard module system. You can always transpile to your preferred module system.
53	:	10.2 Do not use wildcard imports.
54	:	10.3 And do not export directly from an import.
55	:	10.4 Only import from a path in one place. eslint: no-duplicate-imports
56	:	10.5 Do not export mutable bindings. eslint: import/no-mutable-exports
57	:	10.6 In modules with a single export, prefer default export over named export. eslint: import/prefer-default-export
58	:	10.7 Put all imports above non-import statements. eslint: import/first
59	:	10.8 Multiline imports should be indented just like multiline array and object literals.
60	:	10.9 Disallow Webpack loader syntax in module import statements. eslint: import/no-webpack-loader-syntax
61	:	11.1 Don’t use iterators. Prefer JavaScript’s higher-order functions instead of loops like for-in or for-of. eslint: no-iterator no-restricted-syntax
62	:	11.2 Don’t use generators for now.
63	:	11.3 If you must use generators, or if you disregard our advice, make sure their function signature is spaced properly. eslint: generator-star-spacing
64	:	12.1 Use dot notation when accessing properties. eslint: dot-notation jscs: requireDotNotation
65	:	12.2 Use bracket notation [] when accessing properties with a variable.
66	:	12.3 Use exponentiation operator ** when calculating exponentiations. eslint: no-restricted-properties.
67	:	13.1 Always use const or let to declare variables. Not doing so will result in global variables. We want to avoid polluting the global namespace. Captain Planet warned us of that. eslint: no-undef prefer-const
68	:	13.2 Use one const or let declaration per variable. eslint: one-var jscs: disallowMultipleVarDecl
69	:	13.3 Group all your consts and then group all your lets.
70	:	13.4 Assign variables where you need them, but place them in a reasonable place.
71	:	13.5 Don’t chain variable assignments. eslint: no-multi-assign
72	:	13.6 Avoid using unary increments and decrements (++, --). eslint no-plusplus
73	:	13.7 Avoid linebreaks before or after = in an assignment. If your assignment violates max-len, surround the value in parens. eslint operator-linebreak.
74	:	14.1 var declarations get hoisted to the top of their closest enclosing function scope, their assignment does not. const and let declarations are blessed with a new concept called Temporal Dead Zones (TDZ). It’s important to know why typeof is no longer safe.
75	:	14.2 Anonymous function expressions hoist their variable name, but not the function assignment.
76	:	14.3 Named function expressions hoist the variable name, not the function name or the function body.
77	:	14.4 Function declarations hoist their name and the function body.
78	:	For more information refer to JavaScript Scoping & Hoisting by Ben Cherry.
79	:	15.2 Conditional statements such as the if statement evaluate their expression using coercion with the ToBoolean abstract method and always follow these simple rules:
80	:	15.3 Use shortcuts for booleans, but explicit comparisons for strings and numbers.
81	:	15.5 Use braces to create blocks in case and default clauses that contain lexical declarations (e.g. let, const, function, and class). eslint: no-case-declarations
82	:	15.6 Ternaries should not be nested and generally be single line expressions. eslint: no-nested-ternary
83	:	15.7 Avoid unneeded ternary statements. eslint: no-unneeded-ternary
84	:	15.8 When mixing operators, enclose them in parentheses. The only exception is the standard arithmetic operators (+, -, *, & /) since their precedence is broadly understood. eslint: no-mixed-operators
85	:	16.1 Use braces with all multi-line blocks. eslint: nonblock-statement-body-position
86	:	16.2 If you're using multi-line blocks with if and else, put else on the same line as your if block’s closing brace. eslint: brace-style jscs: disallowNewlineBeforeBlockStatements
87	:	16.3 If an if block always executes a return statement, the subsequent else block is unnecessary. A return in an else if block following an if block that contains a return can be separated into multiple if blocks. eslint: no-else-return
88	:	17.1 In case your control statement (if, while etc.) gets too long or exceeds the maximum line length, each (grouped) condition could be put into a new line. The logical operator should begin the line.
89	:	17.2 Don't use selection operators in place of control statements.
90	:	18.1 Use /** ... */ for multi-line comments.
91	:	18.2 Use // for single line comments. Place single line comments on a newline above the subject of the comment. Put an empty line before the comment unless it’s on the first line of a block.
92	:	18.3 Start all comments with a space to make it easier to read. eslint: spaced-comment
93	:	18.5 Use // FIXME: to annotate problems.
94	:	18.6 Use // TODO: to annotate solutions to problems.
95	:	19.1 Use soft tabs (space character) set to 2 spaces. eslint: indent jscs: validateIndentation
96	:	19.2 Place 1 space before the leading brace. eslint: space-before-blocks jscs: requireSpaceBeforeBlockStatements
97	:	19.3 Place 1 space before the opening parenthesis in control statements (if, while etc.). Place no space between the argument list and the function name in function calls and declarations. eslint: keyword-spacing jscs: requireSpaceAfterKeywords
98	:	19.4 Set off operators with spaces. eslint: space-infix-ops jscs: requireSpaceBeforeBinaryOperators, requireSpaceAfterBinaryOperators
99	:	19.5 End files with a single newline character. eslint: eol-last
100	:	19.6 Use indentation when making long method chains (more than 2 method chains). Use a leading dot, which emphasizes that the line is a method call, not a new statement. eslint: newline-per-chained-call no-whitespace-before-property
101	:	19.7 Leave a blank line after blocks and before the next statement. jscs: requirePaddingNewLinesAfterBlocks
102	:	19.8 Do not pad your blocks with blank lines. eslint: padded-blocks jscs: disallowPaddingNewlinesInBlocks
103	:	19.9 Do not add spaces inside parentheses. eslint: space-in-parens jscs: disallowSpacesInsideParentheses
104	:	19.10 Do not add spaces inside brackets. eslint: array-bracket-spacing jscs: disallowSpacesInsideArrayBrackets
105	:	19.11 Add spaces inside curly braces. eslint: object-curly-spacing jscs: requireSpacesInsideObjectBrackets
106	:	19.12 Avoid having lines of code that are longer than 100 characters (including whitespace). Note: per above, long strings are exempt from this rule, and should not be broken up. eslint: max-len jscs: maximumLineLength
107	:	20.1 Leading commas: Nope. eslint: comma-style jscs: requireCommaBeforeLineBreak
108	:	20.2 Additional trailing comma: Yup. eslint: comma-dangle jscs: requireTrailingComma
109	:	21.1 Yup. eslint: semi jscs: requireSemicolons
111	:	22.2 Strings: eslint: no-new-wrappers
112	:	22.3 Numbers: Use Number for type casting and parseInt always with a radix for parsing strings. eslint: radix no-new-wrappers
113	:	22.4 If for whatever reason you are doing something wild and parseInt is your bottleneck and need to use Bitshift for performance reasons, leave a comment explaining why and what you're doing.
114	:	22.5 Note: Be careful when using bitshift operations. Numbers are represented as 64-bit values, but bitshift operations always return a 32-bit integer (source). Bitshift can lead to unexpected behavior for integer values larger than 32 bits. Discussion. Largest signed 32-bit Int is 2,147,483,647:
115	:	22.6 Booleans: eslint: no-new-wrappers
116	:	23.1 Avoid single letter names. Be descriptive with your naming. eslint: id-length
117	:	23.2 Use camelCase when naming objects, functions, and instances. eslint: camelcase jscs: requireCamelCaseOrUpperCaseIdentifiers
118	:	23.3 Use PascalCase only when naming constructors or classes. eslint: new-cap jscs: requireCapitalizedConstructors
119	:	23.4 Do not use trailing or leading underscores. eslint: no-underscore-dangle jscs: disallowDanglingUnderscores
120	:	23.5 Don’t save references to this. Use arrow functions or Function#bind. jscs: disallowNodeTypes
121	:	23.6 A base filename should exactly match the name of its default export.
122	:	23.7 Use camelCase when you export-default a function. Your filename should be identical to your function’s name.
123	:	23.8 Use PascalCase when you export a constructor / class / singleton / function library / bare object.
124	:	23.9 Acronyms and initialisms should always be all capitalized, or all lowercased.
125	:	24.2 Do not use JavaScript getters/setters as they cause unexpected side effects and are harder to test, maintain, and reason about. Instead, if you do make accessor functions, use getVal() and setVal('hello').
126	:	24.3 If the property/method is a boolean, use isVal() or hasVal().
127	:	24.4 It’s okay to create get() and set() functions, but be consistent.
128	:	25.1 When attaching data payloads to events (whether DOM events or something more proprietary like Backbone events), pass an object literal (also known as a "hash") instead of a raw value. This allows a subsequent contributor to add more data to the event payload without finding and updating every handler for the event. For example, instead of:
130	:	26.1 Prefix jQuery object variables with a $. jscs: requireDollarBeforejQueryAssignment
131	:	26.2 Cache jQuery lookups.
132	:	26.4 Use find with scoped jQuery object queries.
133	:	28.2 Do not use TC39 proposals that have not reached stage 3.
134	:	29.1 Use Number.isNaN instead of global isNaN. eslint: no-restricted-globals
135	:	29.2 Use Number.isFinite instead of global isFinite. eslint: no-restricted-globals
