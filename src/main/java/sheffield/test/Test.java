package sheffield.test;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String string = "1234567.233";
		StringBuffer stringBuffer = new StringBuffer(string);
		System.out.println(stringBuffer.indexOf("."));
		stringBuffer.substring(0, stringBuffer.indexOf("."));
		System.out.println(stringBuffer.toString());

	}
}
