package act.mashup.global;

import java.io.PrintWriter;

import javax.servlet.AsyncContext;

public class MashletMaker implements Runnable{
	private AsyncContext ctx;

    public MashletMaker(AsyncContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            // ģ�M�L�r�g��̎��
            //Thread.sleep(10000);  
            PrintWriter out = ctx.getResponse().getWriter();
            out.println("�õ���...XD");
            // �@߅�������ͳ��ؑ�
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
