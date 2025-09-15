public class Polynomial {

    private double[] coefficients;

    
    public Polynomial() {
        coefficients = new double[1];
    }

    public Polynomial(double[] coeffs) {
        coefficients = new double[coeffs.length];
        for (int i = 0; i < coeffs.length; i++) {
            coefficients[i] = coeffs[i];
        }
    }

    
    public Polynomial add(Polynomial second) {
        int maxLen;
        if (this.coefficients.length > second.coefficients.length) {
            maxLen = this.coefficients.length;
        } 
        else {
            maxLen = second.coefficients.length;
        }

        double[] result = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = 0;
            double b = 0;

            if (i < this.coefficients.length) {
                a = this.coefficients[i];
            }

            if (i < second.coefficients.length) {
                b = second.coefficients[i];
            }

            result[i] = a + b;
        }

    return new Polynomial(result);
    }

    public double evaluate(double x) {
        
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }




}
