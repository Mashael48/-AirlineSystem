import java.util.Random;

class RSA {

	// Finds the greatest common divisor of two integers p and q using the extended
	// Euclid's algorithm.
	long[] extended_Euclid(int p, int q) { // x, y, r

		long result[] = new long[3];

		if (q % p == 0) {

			result[0] = 1;
			result[1] = 0;
			result[2] = p;

			return result;

		} else {

			result = extended_Euclid(q % p, p);

			long x = result[0];
			result[0] = result[1] - (q / p) * result[0];
			result[1] = x;

			// System.out.printf("extended_Euclid: x is: %d, y is: %d, r is: %d\n",
			// result[0], result[1], result[2]);
			return result;
		}
	}

	// Finds the modular multiplicative inverse of a modulo m based on the algorithm
	long find_inverse(long a, long m) {

		long inv[] = extended_Euclid((int) a, (int) m);

		if (inv[2] == 1) {
			return inv[0] % m;

		} else {
			System.out.println("No invesre for a exist in Z");
			return 0;
		}

	}

	// Generates a random prime number.
	int random_prime() {

		Random r = new Random();
		int num = r.nextInt(100);

		while (!isPrime(num)) {
			num = r.nextInt(100);
		}

		return num;

	}

	// Check if a number is prime or not.
	boolean isPrime(int p) {

		if (p == 2)
			return true;
		if (p < 2)
			return false;

		for (int i = 2; i < p; i++)
			if (p % i == 0)
				return false;

		return true;
	}

	// Generate Keys method.
	long[] generate_keys() {

		int p = random_prime();
		int q = random_prime();

		while (p == q)
			p = random_prime();

		int n = p * q;

		int n2 = (p - 1) * (q - 1);

		int e;
		long[] r;

		do {

			e = random_prime();
			r = extended_Euclid(e, n2);

		} while (e >= n2 || r[2] != 1 || e < 1);

		long d;
		if (find_inverse(e, n2) == 0)
			return null;
		else
			d = find_inverse(e, n2);

		// n, e, d
		long[] key = { n, e, d };
		return key;
	}

	// Find (b^n mod m) when we are dealing with big numbers, Same as algorithm 5 in
	// 4.2 in the book.
	long modular_exponentiation(long b, long n, long m) {

		long x = 1;
		int bin[] = binary(n);
		long power = b % m;

		for (int i = 0; i < bin.length; i++) {
			if (bin[i] == 1) {
				x = (x * power) % m;
			}
			power = (power * power) % m;

		}

		System.out.println("modular_exponentiation: " + x);
		return x;
	}

	// Takes a string and converts each character to int , ex: Input:KBL, Output:
	// 100111.
	long string_to_int(String text) {

		char alpha[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_' };
		char test2[] = text.toCharArray();
		String str = "";

		for (int i = 0; i < test2.length; i++) {
			for (int j = 0; j < alpha.length; j++) {

				if (test2[i] == alpha[j]) {
					str += j + 10;
					// str += j < 10 ? "0" + j : j;

				}
			}
		}

		return Long.valueOf(str);
	}

	// Convert from int_to_String , ex: Input: 100111, Output: KBL
	String int_to_String(long inttext) {

		char alpha[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_' };

		String str = inttext + "";
		String text = "";

		for (int i = 0; i < str.length(); i += 2) {

			String s = str.substring(i, i + 2);
			text += alpha[Integer.parseInt(s) - 10];

		}

		return text;
	}

	// Encryption method.
	long[] encrypt(String plaintext, long e, long n) {

		String m = "" + string_to_int(plaintext);
		int size = m.length() / 4;
		String arr[] = new String[(m.length() % 4 == 0) ? size : size + 1];

		int j = 0, i = 0;
		for (i = 0, j = 0; j < size; i += 4)
			arr[j++] = m.substring(i, i + 4);

		if (m.length() % 4 != 0)
			arr[j] = m.substring(i, i + 2);

		long result[] = new long[arr.length];

		for (int k = 0; k < arr.length; k++) {

			long l = Long.parseLong(arr[k]);
			result[k] = modular_exponentiation(l, e, n);

		}

		return result;

	}

	String decrypt(long[] ciphertext, long d, long n) {

		String str = "";

		for (int i = 0; i < ciphertext.length; i++)
			str += modular_exponentiation(ciphertext[i], d, n);

		long l = Long.parseLong(str);

		return int_to_String(l);
	}

	int[] binary(long num) {

		String str = "";
		long r;

		do {

			r = num % 2;
			num /= 2;
			str = r + str;

		} while (num != 0);

		int bin[] = new int[str.length()];
		int size = str.length() - 1;

		for (int i = 0; i < str.length(); i++) {
			bin[i] = Integer.parseInt("" + str.charAt(size--));
		}

		return bin;
	}
}// End class
