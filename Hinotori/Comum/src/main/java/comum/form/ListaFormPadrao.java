package comum.form;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ListaFormPadrao {

	
	/**
	 * Função a ser executada quando a tela for fechada.
	 * {@code ListaFormPadrao}.
	 *
	 * @defaultValue null
	 */
	protected ObjectProperty<EventHandler<ActionEvent>> onClose;
	protected static final EventHandler<ActionEvent> DEFAULT_ON_CLOSE = null;

	public final void setOnClose(EventHandler<ActionEvent> value) {
		if ((onClose != null) || (value != null /* DEFAULT_ON_DUPLO_CLIQUE */)) {
			onCloseProperty().set(value);
		}
	}

	public final EventHandler<ActionEvent> getOnClose() {
		return (onClose == null) ? DEFAULT_ON_CLOSE : onClose.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() {
		if (onClose == null) {
			onClose = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onClose",
					DEFAULT_ON_CLOSE);
		}
		return onClose;
	}
	
	protected final void onClose(){
		final EventHandler<ActionEvent> handler = getOnClose();
		if (handler != null)
			handler.handle(new ActionEvent(this, null));
	}
	
}
