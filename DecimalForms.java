package FormulaDetector;

public class DecimalForms implements Constants{
    
}

class Rational_Number extends FormulaDetector {
    int Numerator;
    int Denominator;
    boolean Infinity = false;
    boolean Indefinite = false;
    public Rational_Number (int Numerator, int Denominator) throws ArithmeticException {
        if(Denominator == 0 && Numerator != 0) this.Infinity = true; else this.Infinity = false;
        if(Denominator == 0 && Numerator == 0) this.Indefinite = true; else this.Indefinite = false;
        this.Numerator = Numerator;
        this.Denominator = Denominator;
    }
    /*public static FormulaDetector.Rational_Number Times (Rational_Number leftIn, Rational_Number rightIn) {
        Rational_Number output = Rational_Number(leftIn.Numerator * rightIn.Numerator, leftIn.Denominator * rightIn.Denominator);
        int Gcd = gcd(output.Numerator, output.Denominator);
        output.Denominator /= Gcd;
        output.Numerator /= Gcd;
        return output;}*/
}

class Complex_Number extends FormulaDetector {
    Rational_Number re;
    Rational_Number im;
    public Complex_Number (int re_num, int re_denom, int im_num, int im_denom) {
        this.re = new Rational_Number(re_num, re_denom);
        this.im = new Rational_Number(im_num, im_denom);
    }
}

class Quaternion extends FormulaDetector {
    Rational_Number re;
    Rational_Number imI;
    Rational_Number imJ;
    Rational_Number imK;
    public Quaternion (int re_num, int re_denom, int imI_num, int imI_denom
                      ,int imJ_num, int imJ_denom, int imK_num, int imK_denom) {
        this.re = new Rational_Number(re_num, re_denom);
        this.imI = new Rational_Number(imI_num, imI_denom);
        this.imJ = new Rational_Number(imJ_num, imJ_denom);
        this.imK = new Rational_Number(imK_num, imK_denom);
    }
}

class Complex_Double extends FormulaDetector {
    double re;
    double im;
}

class Quarternion_Double extends FormulaDetector {
    double re;
    double imI;
    double imJ;
    double imK;
}