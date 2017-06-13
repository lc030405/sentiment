package kse.seu.edu.cn.classifier;

import java.io.Serializable;

public class ClassPro implements Serializable{

	private static final long serialVersionUID = 1L;
	private double flag_0 =0;
	private double flag_1 = 0;
	
	public ClassPro(double flag_0, double flag_1) {
		super();
		this.flag_0 = flag_0;
		this.flag_1 = flag_1;
	}
	public ClassPro() {
		super();
	}
	public double getFlag_0() {
		return flag_0;
	}
	public void setFlag_0(double flag_0) {
		this.flag_0 = flag_0;
	}
	public double getFlag_1() {
		return flag_1;
	}
	public void setFlag_1(double flag_1) {
		this.flag_1 = flag_1;
	}
}
