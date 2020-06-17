public enum ProcessStateEnum {
	NONE(""),
	COMPLETED ("Process completed"),
	COMPLETEDWITHERROR ("Process completed with errors"),
	NOTSTARTED ("Process not started");
	
	ProcessStateEnum(final String dsc) {
		this.desc = dsc;
	}

	public String getDesc(ProcessStateEnum enm) {
		return this.desc;
	}

	private final String desc;
}
