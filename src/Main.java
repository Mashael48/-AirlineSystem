
public class Main {

	public static void main(String[] args) {

		// WE NEED TO RUN THE PROGRAM MULTIPULE TIMES TO GET THE RESULT
		RSA r = new RSA();

		try {
			long[] key = r.generate_keys();

			long[] a = r.encrypt("DIS_MATH", key[1], key[0]);

			System.out.printf("\ngenerate_keys: (e) value is: %d, And (n) value is: %d\n", key[1], key[0]);

			System.out.println("The encrypted message of \"DIS_MATH\" is:");
			for (int i = 0; i < a.length; i++)
				System.out.print(a[i] + " ");

			System.out.printf("\n\ngenerate_keys: (d) value is: %d, And (n) value is: %d\n", key[2], key[0]);

			System.out.print("\nThe decrypted message of ");
			for (int i = 0; i < a.length; i++)
				System.out.print(a[i] + " ");

			System.out.println(" is:");
			System.out.println(r.decrypt(a, key[2], key[0]));
		} catch (NullPointerException e) {
			System.out.println(e.toString());
		}
	}// main
}// class