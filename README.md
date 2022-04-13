# simplecli

Implemented build-ins:
- [cat](src/main/java/ru/itmo/simplecli/executor/constructors/Cat.java)
- [echo](src/main/java/ru/itmo/simplecli/executor/constructors/Echo.java)
- [exit](src/main/java/ru/itmo/simplecli/executor/constructors/Exit.java)
- [grep](src/main/java/ru/itmo/simplecli/executor/constructors/Grep.java)
- [pwd](src/main/java/ru/itmo/simplecli/executor/constructors/Pwd.java)
- [wc](src/main/java/ru/itmo/simplecli/executor/constructors/WC.java)
- [variable assignment](src/main/java/ru/itmo/simplecli/executor/constructors/Assignment.java)

[Main](src/main/java/ru/itmo/simplecli/Main.java) runs loop until `exit` command is given.
If the entered string contains incomplete quotes, it asks to continue typing.

[Parser](src/main/java/ru/itmo/simplecli/executor/Parser.java) supports double and single quotes.
It also performs substitution for values beginning with `$` if it is not surrounded by single quotes.

The CLI [supports](src/main/java/ru/itmo/simplecli/executor/PipedCommand.java) a pipeline of commands, separated by a sign `|`,
each command receives as input the output of the previous one.