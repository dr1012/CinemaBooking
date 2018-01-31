package application.views.plan.util;

import com.sun.javafx.tk.Toolkit;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;



/**
*************************************************************************************
* This class is based on the following source:
* Title: Select multiple ListView item without pressing CTRL (Shortcut key)
* Author: Uluk Biy
* Date: 21/8/2015
* Availability: https://stackoverflow.com/questions/32136669/select-multiple-listview-item-without-pressing-ctrl-shortcut-key
*
*************************************************************************************
*
*/



public class MultiSelect {
	
	
	public static EventHandler<MouseEvent> multiSelectEventHandler() {
		
		EventHandler<MouseEvent> eventHandler = ( event ) ->
		{
		    if ( !event.isShortcutDown() )
		    {
		        Event.fireEvent( event.getTarget(), MultiSelect.cloneMouseEvent( event ) );
		        event.consume();
		    }
		};
		
		return eventHandler;
	}
	
	
	
	public static MouseEvent cloneMouseEvent( MouseEvent event )
	{
	    switch (Toolkit.getToolkit().getPlatformShortcutKey())
	    {
	        case SHIFT:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    true,
	                    event.isControlDown(),
	                    event.isAltDown(),
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case CONTROL:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    true,
	                    event.isAltDown(),
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case ALT:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    event.isControlDown(),
	                    true,
	                    event.isMetaDown(),
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        case META:
	            return new MouseEvent(
	                    event.getSource(),
	                    event.getTarget(),
	                    event.getEventType(),
	                    event.getX(),
	                    event.getY(),
	                    event.getScreenX(),
	                    event.getScreenY(),
	                    event.getButton(),
	                    event.getClickCount(),
	                    event.isShiftDown(),
	                    event.isControlDown(),
	                    event.isAltDown(),
	                    true,
	                    event.isPrimaryButtonDown(),
	                    event.isMiddleButtonDown(),
	                    event.isSecondaryButtonDown(),
	                    event.isSynthesized(),
	                    event.isPopupTrigger(),
	                    event.isStillSincePress(),
	                    event.getPickResult()
	            );

	        default:
	            return event;

	    }
	}

}
