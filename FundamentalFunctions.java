package FormulaDetector;

class FundamentalFunctions implements Constants {
    double LeftValue;

public static double floor_re (double x) {
    double floor_re_out = 0;
        if (x > 0) {while (x >= 1) {x--; floor_re_out++;}}
        else if (x < 0) {while (x < 0) {x++; floor_re_out--;}}
    return floor_re_out;}


public static double fractional_part_re (double x) {
    return x - floor_re (x);
}//end def}

public static double mod_re (double leftIn, double rightIn) {
    return leftIn - rightIn * floor_re(leftIn / rightIn);
}

public static int gcd (int x, int y) {
    int temp;
    while (y != 0) {x %= y;
        temp = x; x = y; y = temp;}
    return x;
}

public static int max (int x, int y) {
    if(x > y) return x;
    else return y;
}

public static int min (int x, int y) {
    if(x < y) return x;
    else return y;
}

public static double power_int (double x, int y) {
    double power_int_out = 1;
    if (y > 0) {
    for (int power_int_n = 0; power_int_n < y; power_int_n++) {
    power_int_out *= x;}//end loop for
    return power_int_out;}
    else if (y < 0) {
    for (int power_int_n = 0; power_int_n < y; power_int_n++) {
    power_int_out /= x;}//end loop for
    return power_int_out;}
    else return 1;
}//end def

public static double factorial_int (int x) {
    if (x >= 0) {
    double factorial_int_out = 1;
    for (int factorial_int_n = 1; factorial_int_n <= x; factorial_int_n++) {
    factorial_int_out *= (double)factorial_int_n;}//end loop for
    return factorial_int_out;}
    else {System.out.println("Invalid factorial.\n"); return 0;}
}//end def

public static double abs_re (double x) {
    if (x < 0) {x = -x;}
    return x;}
            
public static double sign_re (double x) {
    double sign_re_out;
    if (x < 0) {sign_re_out = -1;}
    else if (x > 0) {sign_re_out = 1;}
    else sign_re_out = 0;
    return sign_re_out;
}
       
public static double sqrt_re (double x) {
    double sqrt_re_out = 0, error = 1e-12;
    while (sqrt_re_out * sqrt_re_out <= x) {
    sqrt_re_out++;}
    while (abs_re(sqrt_re_out * sqrt_re_out - x) >= error) {
    sqrt_re_out = (sqrt_re_out + x / sqrt_re_out) / 2;}
    return sqrt_re_out;
}//end def
            
public static double cbrt_re (double x) {
    double cbrt_re_out = 0, error = 1e-12;
    while (power_int(cbrt_re_out, 3) <= x) {
    cbrt_re_out++;}while (abs_re(power_int (cbrt_re_out, 3) - x) >= error) {
    cbrt_re_out = (2 * cbrt_re_out + x / power_int (cbrt_re_out, 2)) / 3;}
    return cbrt_re_out;
    }//end def

public static double exp_re (double x) {
    double exp_re_out = 1, error = 1e-15, sav;
    sav = floor_re (x);
    x = fractional_part_re (x);
    int order = 1;
    while (abs_re(power_int(x, order) / factorial_int(order)) >= error) {
    exp_re_out += power_int (x, order) / factorial_int (order);
    order++;}
    if (sav > 0) {
    for (int temp = 0; temp < sav; temp++)
    exp_re_out *= E_low_prec;}
    else if (sav < 0) {
    sav = -sav;
    for (int temp = 0; temp < sav; temp++)
    exp_re_out *= E_re_low_prec;}
    return exp_re_out;
    }
        
public static double log_pos (double x) {
    double log_pos_out = 0, error = 1e-15, offset = 0;
    if (x > 0) {while (x >= E_low_prec) { x *= E_re_low_prec; offset++;}
    while (x <= E_re_low_prec) { x *= E_low_prec; offset--;}
    while (abs_re(exp_re(log_pos_out) - x) >= error) {log_pos_out += x / exp_re(log_pos_out) - 1;}
    return log_pos_out + offset;}
    else return 0;}
        
public static double power_pos (double x, double y) {
    if (x > 0) {return exp_re(y * log_pos(x));}
    else if (x == 0) {if (y > 0) return 0; else {System.out.println("Unexpected Input.\n"); return 0;}}
    else {System.out.println("Unexpected Input.\n"); return 0;}}
        
public static double OddQ (int x) {
    if (x % 2 == 0) return 0;
    else return 1;}
        
public static double sin_re (double x) {
    int order = 3, flip = 0;
    if (x < 0) {x = -x; flip++;}     
    while (x >= Two_Pi_low_prec) {x -= Two_Pi_low_prec;}
    if (x >= Pi_low_prec) {x = Two_Pi_low_prec - x; flip++;}
    if (x >= Pi_2_low_prec) {x = Pi_low_prec - x;}
    double sin_re_out = x, error = 1e-16;
    do {sin_re_out += power_int(-1., (order - 1) / 2) * power_int(x, order) / factorial_int(order);
    order += 2;} while (abs_re(power_int(x, order) / factorial_int(order)) >= error);
    return sin_re_out * power_int(-1., flip);}
        
public static double cos_re (double x) {
    x = Pi_2_low_prec - x;
    return sin_re (x);}
        
public static double tan_re (double x) {
    return sin_re (x) / cos_re (x);}
        
public static double cot_re (double x) {
    return cos_re (x) / sin_re (x);}
        
public static double sec_re (double x) {
    return 1 / cos_re (x);}
        
public static double csc_re (double x) {
    return 1 / sin_re (x);}
        
public static double sinh_re (double x) {
    return (exp_re (x) - exp_re (-x)) / 2;}
        
public static double cosh_re (double x) {
    return (exp_re (x) + exp_re (-x)) / 2;}
        
public static double tanh_re (double x) {
    return sinh_re (x) / cosh_re (x);}
        
public static double coth_re (double x) {
    return cosh_re (x) / sinh_re (x);}
        
public static double sech_re (double x) {
    return 1 / cosh_re (x);}
        
public static double csch_re (double x) {
    return 1 / sinh_re (x);}
        
public static double arcsin_re (double x) {
    Boolean isNeg = false, isCos = false; double error = 1e-15;
    if (abs_re(x) <= 1) {
        if (x < 0) {isNeg = true; x = -x;} 
        if (x >= Sqrt_2_re_low_prec) {isCos = true; x = sqrt_re (1 - x * x);}
        double arcsin_re_out = x;
        while (abs_re(sin_re(arcsin_re_out) - x) >= error)
            {arcsin_re_out += (x - sin_re(arcsin_re_out)) / cos_re(arcsin_re_out);}
        if (isCos) arcsin_re_out = Pi_2_low_prec - arcsin_re_out;
        if (isNeg) arcsin_re_out = -arcsin_re_out;
        return arcsin_re_out;
        }
    else {System.out.println("Invalid input.\n"); return 0;}}
        
public static double arccos_re (double x) {
    return Pi_2_low_prec - arcsin_re(x);}
        
public static double arctan_re (double x) {
    Boolean isNeg = false, isCos = false; double error = 1e-15;
    if (x < 0) {isNeg = true; x = -x;} 
    if (x >= 1) {isCos = true; x = 1 / x;}
    double arctan_re_out = x;
    while (abs_re(tan_re(arctan_re_out) - x) >= error)
        {arctan_re_out += (x - tan_re(arctan_re_out)) * (cos_re(2*arctan_re_out) + 1) / 2;}
    if (isCos) arctan_re_out = Pi_2_low_prec - arctan_re_out;
    if (isNeg) arctan_re_out = -arctan_re_out;
    return arctan_re_out;}
        
public static double arccot_re (double x) {
    if (x != 0) return arctan_re (1 / x);
    else return Pi_2_low_prec;}
        
public static double arcsec_re (double x) {
    return arccos_re (1 / x);}
        
public static double arccsc_re (double x) {
    return arcsin_re (1 / x);}}