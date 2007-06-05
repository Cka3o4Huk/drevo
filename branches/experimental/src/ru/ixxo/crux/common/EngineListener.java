package ru.ixxo.crux.common;

import java.util.EventListener;

public interface EngineListener extends EventListener {
	public void engineOutcomingEventProcess(EngineEvent event);
	public void engineIncomingEventProcess(EngineEvent event);
}
