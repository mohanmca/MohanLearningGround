import hudson.model.*
import hudson.EnvVars
import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.net.URL
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.util.RemotingDiagnostics
import jenkins.model.Jenkins

node {
  sh "set"
  stage('let us try ') {
        
        Jenkins.instance.pluginManager.plugins.collect{plugins -> println(plugins)}
        TaskListener log;
        
        for (ToolDescriptor<?> desc : ToolInstallation.all()) {
        	for (ToolInstallation inst : desc.getInstallations()) {
        		println ('\tTool Name: ' + inst.getName());
                println ('\t\tTool Home: ' + inst.translateFor(Jenkins.instance,log));
            }  
        }  
        
        
        //printAllMethods(Jenkins.instance)
        System.getProperties().collect{props -> println(props)}
        
        
        println System.getenv
        
        groovy_script = '''
        println System.getenv("PATH")
        println "uname -a".execute().text
        '''.trim()
         
        String result
        Jenkins.instance.slaves.collect { agent ->    result = RemotingDiagnostics.executeGroovy(groovy_script, agent.channel) }
        println result      
  }
}

  def printAllMethods( obj )
  {
            if( !obj ){
        		println( "Object is null\r\n" );
        		return;
            }
        	if( !obj.metaClass && obj.getClass() ){
                printAllMethods( obj.getClass() );
        		return;
            }
        	def str = "class ${obj.getClass().name} functions:\r\n";
        	obj.metaClass.methods.name.unique().each{ 
        		str += it+"();\r\n"; 
        	}
        	println "${str}";
}