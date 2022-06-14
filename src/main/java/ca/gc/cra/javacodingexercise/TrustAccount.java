package ca.gc.cra.javacodingexercise;

public class TrustAccount {

	private String tan;
	private String not;
	private String city;
	private String province;
	private int pi;
	private int ri;
	
	public TrustAccount(String tan, String not, String city, String province, int pi, int ri) {
		this.setTan(tan);
		this.setNot(not);
		this.setCity(city);
		this.setProvince(province);
		this.setPi(pi);
		this.setRi(ri);
	}
	
	public TrustAccount(String tan, String province, int pi, int ri) {
		this(tan, "", "", province, pi, ri);
	}
	
	public TrustAccount() {
		this(Settings.DEFAULT_TAN, "", "", Settings.DEFAULT_PROVINCE, Settings.DEFAULT_PI, Settings.DEFAULT_RI);
	}
	
	/** Get Trust Account Number.
	 * @return the tan
	 */
	public String getTan() {
		return tan;
	}
	
	/** Set Trust Account Number.
	 * @param tan the tan to set
	 */
	public void setTan(String tan) {
		this.tan = tan;
	}
	
	/** 
	 * @return the not
	 */
	public String getNot() {
		return not;
	}
	/**
	 * @param not the not to set
	 */
	public void setNot(String not) {
		this.not = not;
	}
	
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * @return the pi
	 */
	public int getPi() {
		return pi;
	}
	/**
	 * @param pi the pi to set
	 */
	public void setPi(int pi) {
		this.pi = pi;
	}
	
	/**
	 * @return the ri
	 */
	public int getRi() {
		return ri;
	}
	
	/**
	 * @param ri the ri to set
	 */
	public void setRi(int ri) {
		this.ri = ri;
	}
}
