package Labs.Lab_8;

import java.io.Serializable;


public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private byte id;
	public byte getID() {
		return id;
	}
	
	protected Message() {
		assert( false );
	}
	
	protected Message( byte id ) {
		
		assert( Protocol.validID( id ) );
		
		this.id = id;
	}
}
