# ANTLR example - While0 language

![Build status on Travis](https://api.travis-ci.com/kdevo/ise-ti-while0.svg?branch=master)

This project has been created at the *FH Aachen - University of Applied Sciences* (Faculty 5) in the subject Theoretical Computer Science (German: "Theoretische Informatik").

The solved exercises can be found in the [docs directory](./docs/exercises).

## Used technologies

1. **ANTLR**: Generate Java code based on [While0 grammar file](src/main/antlr/me/kdevo/ise/ti/while0/While0.g4)
2. **Kotlin**: Use generated Java source code (is placed in `build/generated-src`)
3. **Gradle**: Build tool used to automatize the workflow and ANTLR code generation described above, see [build.gradle](build.gradle)

## Goals

- Show how to use the tech-stack described above
- Show how ANTLR can be utilized to compile code (*While0 to URM*, see [Wikipedia](https://en.wikipedia.org/wiki/LOOP_(programming_language) for more information)
- Use ANTLR's [Visitor pattern](./src/main/kotlin/me/kdevo/ise/ti/while0/While0Visitor.kt) and [Error Listener](./src/main/kotlin/me/kdevo/ise/ti/while0/While0ErrorListener.kt)
- Effectively use Kotlin's extensions and advantages
- Keep the project simple and small
