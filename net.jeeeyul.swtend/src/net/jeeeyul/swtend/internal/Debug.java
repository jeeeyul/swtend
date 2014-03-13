package net.jeeeyul.swtend.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.core.runtime.Platform;

public class Debug extends PrintStream {
	private static final boolean DEBUG;
	private static OutputStream stream;
	private static final Debug INSTANCE;

	static {
		if(Platform.isRunning()){
			DEBUG = "true".equalsIgnoreCase(Platform.getDebugOption("net.jeeeyul.swtend/debug"));
		}else{
			DEBUG = false;
		}
		
		if (DEBUG) {
			stream = System.out;
		} else {
			stream = new OutputStream() {
				@Override
				public void write(int arg0) throws IOException {
				}
			};
		}
		INSTANCE = new Debug(stream);
	}

	public static Debug getInstance() {
		return INSTANCE;
	}

	private Debug(OutputStream os) {
		super(os);
	}
}
