package ground.java;

import com.jcraft.jsch.*;

import java.io.InputStream;

public class ssh {
    public static void main(String[] args) throws Exception {
        JSch jsch=new JSch();
        Session session=jsch.getSession(user, host, 22);
        UserInfo ui=new UserInfo();
        session.setUserInfo(ui);
        session.connect();
        Channel channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);

        InputStream in=channel.getInputStream();

        channel.connect();


        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
            }
            if(channel.isClosed()){
                if(in.available()>0) continue;
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }
        channel.disconnect();
        session.disconnect();
    }
}
