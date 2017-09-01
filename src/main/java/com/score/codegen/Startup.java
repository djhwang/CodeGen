
package com.score.codegen;

public class Startup{
	public static void main(String[] args) throws Exception{
		CodeGenerator codeGen = new CodeGenerator();
		codeGen.init();
		codeGen.parse();
		codeGen.generateStub();
	}
}
