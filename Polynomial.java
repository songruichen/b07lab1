import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {

    private double[] coefficients;
    private int[] exponents;


    
    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coeffs, int[] expos) {

    int n = coeffs.length;
    double[] c = new double[n];
    int[] e = new int[n];
    for (int i = 0; i < n; i++) {
        c[i] = coeffs[i];
        e[i] = expos[i];
    }
    for (int i = 0; i < n-1; i++) {
        for (int j = i+1; j < n; j++) {
            if (e[i] > e[j]) {
                int tmpE = e[i];
                e[i] = e[j];
                e[j] = tmpE;
                double tmpC = c[i];
                c[i] = c[j];
                c[j] = tmpC;
            }
        }
    }

    this.coefficients = c;
    this.exponents = e;
}


    
    public Polynomial add(Polynomial second) {

        if (this.coefficients.length == 0) return second;
        if (second.coefficients.length == 0) return this;
        
        int last1 = this.exponents[exponents.length -1];
        int last2 = second.exponents[second.exponents.length -1];
        
        double[] list1 = new double[last1 + 1];
        double[] list2 = new double[last2 + 1];

        for(int i = 0; i < this.exponents.length; i++){
            list1[this.exponents[i]] = this.coefficients[i];
        }
        for(int i = 0; i < second.exponents.length; i++){
            list2[second.exponents[i]] = second.coefficients[i];
        }

        int maxLen;
        if (list1.length > list2.length) {
            maxLen = list1.length;
        } 
        else {
            maxLen = list2.length;
        }

        double[] result = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = 0;
            double b = 0;

            if (i < list1.length) {
                a = list1[i];
            }

            if (i < list2.length) {
                b = list2[i];
            }

            result[i] = a + b;
        }

        int count = 0;
        for (int i = 0; i < result.length; i++) {
            if(result[i] != 0){
                count++;
            }
        }
        double[] new_coefficients = new double[count];
        int[] new_exponents = new int[count];

        int start = 0;
        for (int j = 0; j < result.length; j++) {
            if(result[j] != 0){
                new_coefficients[start] = result[j];
                new_exponents[start] = j;
                start++;
            }
            
        }

    return new Polynomial(new_coefficients, new_exponents);
    }

    public double evaluate(double x) {
        
        if (coefficients.length == 0 || exponents.length == 0) {
        return 0.0; // zero polynomial always evaluates to 0
        }
        
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }


    public Polynomial multiply(Polynomial second){
        if (this.coefficients.length == 0 || second.coefficients.length == 0) {
            return new Polynomial(new double[]{}, new int[]{}); 
        }
        int max_length = this.coefficients.length * second.coefficients.length;
        
        double[] list1 = new double[max_length];
        int[] list2 = new int[max_length];

        int size = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            for(int j = 0; j < second.coefficients.length; j++){
                double temp1 = this.coefficients[i] * second.coefficients[j];
                int temp2 = this.exponents[i] + second.exponents[j];

                boolean sign = false;

                for(int k= 0; k < size; k++){
                    if (list2[k] == temp2){
                        list1[k] += temp1;
                        sign = true;
                        break;
                    }
                }
                if(sign == false){
                    list1[size] = temp1;
                    list2[size] = temp2;
                    size++;
                }

            }
            
        }
        double[] final1 = new double[size];
        int[] final2 = new int[size];

        for(int m = 0; m < size; m++){
            final1[m] = list1[m];
            final2[m] = list2[m];
        }

        return new Polynomial(final1, final2);

    }

    public Polynomial(File file) throws IOException {
    Scanner s = new Scanner(file);

    if (!s.hasNextLine()) {
        // Empty file = zero polynomial
        coefficients = new double[0];
        exponents = new int[0];
        s.close();
        return;
    }
    String line = s.nextLine().replace(" ", "");
    s.close();

    line = line.replace("-", "+-");
    String[] sections = line.split("\\+");

    coefficients = new double[sections.length];
    exponents = new int[sections.length];

    for (int i = 0; i < sections.length; i++) {
        String section = sections[i];
        if (section.isEmpty()) {
            continue;
        }

        if (section.contains("x")) {
            String[] parts = section.split("x");

            if (parts.length == 1 || parts[1].equals("")) {
                exponents[i] = 1;
            } else {
                exponents[i] = Integer.parseInt(parts[1]);
            }

            if (parts[0].equals("") || parts[0].equals("+")) {
                coefficients[i] = 1;
            } else if (parts[0].equals("-")) {
                coefficients[i] = -1;
            } else {
                coefficients[i] = Double.parseDouble(parts[0]);
            }

        } else {
            coefficients[i] = Double.parseDouble(section);
            exponents[i] = 0;
            }   
    }
    }

    
    public void saveToFile(String filename) throws IOException {

    StringBuilder text = new StringBuilder();

    for (int i = 0; i < this.coefficients.length; i++) {
        double coeff = this.coefficients[i];
        int exp = this.exponents[i];

        if (coeff == 0) continue; 

        if (text.length() > 0 && coeff > 0) {
            text.append("+");
        }

        if (exp == 0) {
            text.append(coeff); 
        } else if (exp == 1) {
            if (coeff == 1) {
                text.append("x");
            } else if (coeff == -1) {
                text.append("-x");
            } else {
                text.append(coeff).append("x");
            }
        } else {
            if (coeff == 1) {
                text.append("x").append(exp);
            } else if (coeff == -1) {
                text.append("-x").append(exp);
            } else {
                text.append(coeff).append("x").append(exp);
            }
        }
    }

    FileWriter writer = new FileWriter(filename);
    writer.write(text.toString());
    writer.close();
}

    public double[] getCoefficients() {
        return coefficients;
    }

    public int[] getExponents() {
        return exponents;
    }





}
