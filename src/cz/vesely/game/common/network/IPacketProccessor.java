package cz.vesely.game.common.network;

public interface IPacketProccessor {

	boolean isMainThread();
	
	void addTask(Runnable runnable);
	
}
