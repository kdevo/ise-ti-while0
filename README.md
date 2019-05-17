# ANTLR example for While0 language

## Used technologies:
1. ANTLR: Generate Java code based on [While0 grammar file](src/main/antlr/me/kdevo/ise/ti/while0/While0.g4)
2. Kotlin: Use generated Java source code (is placed in `build/generated-src`)
3. Gradle: Build tool used to automatize the workflow and ANTLR code generation described above, see [build.gradle](build.gradle)
