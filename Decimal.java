package FormulaDetector;
import java.util.Scanner;

class RealDecimalNumberException extends Exception {
    public void NotReal() {System.out.println("Input is not a real number.");}
}

class RationalNumberException extends Exception {
    public void Infinity() {System.out.println("Input is infinity.");}
    public void Undetermined() {System.out.println("Input is undetermination.");}
}

class SciDec implements Constants{
    StringBuffer FullDec;
    int index;
    boolean isNeg;//if true, this decimal is negative

    static SciDec Zero = new SciDec("0",0,false), One = new SciDec("1",0,false);

    SciDec (String FullDec) {
        this.FullDec = new StringBuffer(FullDec);
        this.index = 0;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = false;
        this.Simplify();
        this.RemoveDot();
    }

    SciDec (StringBuffer FullDec) {
        this.FullDec = FullDec;
        this.index = 0;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = false;
        this.RemoveDot();
    }

    SciDec (String FullDec, int index) {
        this.FullDec = new StringBuffer(FullDec);
        this.index = index;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = false;
        this.Simplify();
        this.RemoveDot();
    }

    SciDec (StringBuffer FullDec, int index) {
        this.FullDec = FullDec;
        this.index = index;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = false;
        this.Simplify();
        this.RemoveDot();
    }

    SciDec (StringBuffer FullDec, int index, boolean isNeg) {
        this.FullDec = FullDec;
        this.index = index;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = isNeg;
        this.Simplify();
        this.RemoveDot();
    }

    SciDec (String FullDec, int index, boolean isNeg) {
        this.FullDec = new StringBuffer(FullDec);
        this.index = index;
        if(FullDec.charAt(0)=='-') {this.isNeg = true; this.FullDec.delete(0, 1);}
        else this.isNeg = isNeg;
        this.Simplify();
        this.RemoveDot();
    }

    SciDec (Decimal NotSciDec) {
        int n;
        NotSciDec.DecimalSimplify();
        if(NotSciDec.DotFound() && NotSciDec.charAt(0) != '-') {n = NotSciDec.DotIndex(); this.index = n - NotSciDec.length() + 1;
            NotSciDec.DecimalForm.delete(n, n + 1); NotSciDec.DecimalSimplify(); this.isNeg = false;}
        else if(!NotSciDec.DotFound()){this.index = 0; this.isNeg = (NotSciDec.charAt(0)=='-')?true:false;}
        else {n = NotSciDec.DotIndex(); this.index = n - NotSciDec.length() + 1;
            NotSciDec.DecimalForm.delete(n, n + 1); NotSciDec.DecimalSimplify(); this.isNeg = true;}
        NotSciDec.DecimalSimplify();
        this.FullDec = NotSciDec.DecimalForm;
        if(this.isNeg) this.FullDec.delete(0, 1);
    }

    public void Read () {
        if(this.isNeg) System.out.println("-"+this.FullDec+"*10^"+this.index);
        else System.out.println(this.FullDec+"*10^"+this.index);
    }

    public void Read (int Style) {
        switch(Style){
            case  0:if(this.isNeg) System.out.println("-"+this.FullDec+"E"+this.index);
                    else System.out.println(this.FullDec+"E"+this.index); break;
            case  1:if(this.isNeg) System.out.println("-"+this.FullDec+"*10^"+this.index);
                    else System.out.println(this.FullDec+"*10^"+this.index); break;
            case  3:if(this.isNeg && this.index != 0) System.out.println("-"+this.FullDec+"*10^"+this.index);
                    else if(this.index != 0) System.out.println("-"+this.FullDec+"*10^"+this.index);
                    else if(this.isNeg) System.out.println("-"+this.FullDec);
                    else System.out.println("-"+this.FullDec); break;
            case  2:default:
                    if(this.isNeg && this.index != 0) System.out.println("-"+this.FullDec+"E"+this.index);
                    else if(this.index != 0) System.out.println("-"+this.FullDec+"E"+this.index);
                    else if(this.isNeg) System.out.println("-"+this.FullDec);
                    else System.out.println("-"+this.FullDec); break;
            }
    }

    public void ChangeIndex (int newIndex) {
        this.index = newIndex;
    }

    public void AddIndex () {
        this.index++;
    }

    public int length() {
        return this.FullDec.length();
    }

    public char charAt(int index) {
        return this.FullDec.charAt(index);
    }

    public void delete(int start, int end) {
        this.FullDec.delete(start, end);
    }

    public void insert(int offset, Object obj) {
        this.FullDec.insert(offset, obj);
    }

    public void append(Object obj) {
        this.FullDec.append(obj);
    }

    public static SciDec NoSignAdd (SciDec LeftDec, SciDec RightDec) {//only allows positive numbers to add
        LeftDec.Simplify();RightDec.Simplify();
        if(RightDec.Equals(Zero)) return LeftDec;
        else if(LeftDec.Equals(Zero)) return RightDec;
        LeftDec.RemoveDot(); RightDec.RemoveDot();
        StringBuffer Ori = new StringBuffer();
        StringBuffer Adder = new StringBuffer();
        StringBuffer ResultO = new StringBuffer();
        int index, offset, temp = 0, nextNum, nextUp = 0;
        if(LeftDec.index > RightDec.index) {Ori.append(LeftDec.FullDec); Adder.append(RightDec.FullDec);
            index = RightDec.index; offset = LeftDec.index - RightDec.index;}
        else {Ori.append(RightDec.FullDec); Adder.append(LeftDec.FullDec);
            index = LeftDec.index; offset = RightDec.index - LeftDec.index;}
        for(temp = 0; temp < offset; temp++) {Ori.append('0');}
        if(Ori.length() < Adder.length()) {offset = Adder.length() - Ori.length();
            for(temp = 0; temp < offset; temp++) {Ori.insert(0, '0');}}
        else {offset = Ori.length() - Adder.length();
            for(temp = 0; temp < offset; temp++) {Adder.insert(0, '0');}}
        temp = Ori.length();
        while(temp > 0){
            nextNum = (int)Ori.charAt(temp-1) + (int)Adder.charAt(temp-1) + nextUp - 96;
            nextUp = nextNum / 10;
            ResultO.insert(0, (char)(nextNum % 10 + 48));
            temp--;
        }
        if(temp == 0 && nextUp != 0) ResultO.insert(0, (char)(nextUp + 48));
        SciDec ResultS = new SciDec(ResultO, index);
        return ResultS;
    }

    public static SciDec InverseNumber (SciDec Number) {
        Number.RemoveDot();
        StringBuffer Inversion = new StringBuffer();
        int n = Number.length();
        for(int temp = 0; temp < n; temp++) {if(Number.charAt(temp)!='.')Inversion.append(subtractionCompliments[(int)Number.charAt(temp)-48]);}
        return new SciDec(Inversion, Number.index, Number.isNeg);
    }

    public static SciDec NoSignSubtract (SciDec LeftDec, SciDec RightDec) {//only allows a bigger pos left to subtract smaller pos right 
        LeftDec.Simplify(); RightDec.Simplify();
        LeftDec.RemoveDot(); RightDec.RemoveDot();
        if(RightDec.length() == 1 && RightDec.charAt(0) == '0') return LeftDec;
        else if(LeftDec.length() == 1 && LeftDec.charAt(0) == '0') return new SciDec(RightDec.FullDec, RightDec.index, !RightDec.isNeg);
        SciDec InverseRightDec = InverseNumber(RightDec), Inversion = NoSignAdd(InverseRightDec, new SciDec("1", InverseRightDec.index));
        //InverseRightDec.Read(); Inversion.Read();
        //Inversion.RemoveDot();
        Inversion.Simplify();
        SciDec Result = NoSignAdd(LeftDec, Inversion);
        //LeftDec.Read();
        //Result.Read();
        int t = Result.length() - 1 - (RightDec.index + RightDec.length() - Result.index), temp; 
        //System.out.println(t+" length:"+Result.length()+" right index:"+RightDec.index+" right length:"+RightDec.length()+" result index:"+Result.index);
        while(t >= 0) {if(Result.charAt(t) != '0') {temp = (int)Result.charAt(t); temp--;
            Result.delete(t, t + 1); Result.insert(t, (char)temp); break;} else {Result.delete(t, t + 1); Result.insert(t, '9'); t--;}}
        Result.Simplify();
        return Result;
    }

    public static SciDec Add (SciDec LeftDec, SciDec RightDec) {
        if(RightDec.Equals(Zero)) return LeftDec;
        else if(LeftDec.Equals(Zero)) return RightDec;
        if(!LeftDec.isNeg && !RightDec.isNeg) return NoSignAdd(LeftDec, RightDec);
        else if (!LeftDec.isNeg && RightDec.isNeg) {if(LeftDec.NoSignMoreThan(RightDec)) return NoSignSubtract(LeftDec, RightDec);
            else {SciDec Result = NoSignSubtract(RightDec, LeftDec); Result.isNeg = true; return Result;}}
        else if (LeftDec.isNeg && !RightDec.isNeg) {if(LeftDec.NoSignMoreThan(RightDec)) {SciDec Result = NoSignSubtract(LeftDec, RightDec);
            Result.isNeg = true; return Result;} else return NoSignSubtract(RightDec, LeftDec);}
        else {SciDec Result = NoSignAdd(LeftDec, RightDec); Result.isNeg = true; return Result;}
    }

    public SciDec Add (SciDec RightDec) {
        return Add(this, RightDec);
    }

    public static SciDec Subtract (SciDec LeftDec, SciDec RightDec) {
        if(RightDec.length() == 1 && RightDec.charAt(0) == '0') return LeftDec;
        else if(LeftDec.length() == 1 && LeftDec.charAt(0) == '0') return new SciDec(RightDec.FullDec, RightDec.index, !RightDec.isNeg);
        SciDec RightTemp = new SciDec(RightDec.FullDec, RightDec.index, !RightDec.isNeg);
        return Add(LeftDec, RightTemp);
    }

    public SciDec Subtract (SciDec RightDec) {
        return Subtract(this, RightDec);
    }

    public static SciDec SimpleTimes (SciDec Dec, int Digit) {//only allows when Digit be in 0 ~ 9
        Dec.RemoveDot();
        if (Digit == 0) return new SciDec("0",0);
        else if (Digit == 1) return Dec.Copy();
        else {StringBuffer newDec = new StringBuffer(); int nextNum, nextUp = 0, index = Dec.length();
            while(index > 0) {nextNum = ((int)Dec.charAt(index - 1) - 48) * Digit + nextUp; nextUp = nextNum / 10;
                newDec.insert(0,(char)(nextNum % 10 + 48)); index--;}
            newDec.insert(0, nextUp);
            Simplify(newDec);
        return new SciDec(newDec, Dec.index, Dec.isNeg);}
    }

    public static SciDec SimpleTimes (SciDec Dec, char Digit) {
        return SimpleTimes(Dec, (int)Digit - 48);
    }

    public static SciDec Times (SciDec LeftDec, SciDec RightDec) {
        LeftDec.RemoveDot(); RightDec.RemoveDot();
        LeftDec.Simplify(); RightDec.Simplify();
        int offset = RightDec.length() - 1; Boolean isNeg = LeftDec.isNeg ^ RightDec.isNeg;
        SciDec Sum = new SciDec("0", 0, false), Adder;
        for(int i = 0; i < RightDec.length(); i++) {Adder = SimpleTimes(LeftDec, RightDec.charAt(offset - i)); Adder.isNeg = false; Adder.index = i + LeftDec.index; //Adder.Read(); System.out.println(i+" "+LeftDec.index); LeftDec.Read();
             Sum = NoSignAdd(Sum,Adder);}
        Sum.index += RightDec.index; Sum.isNeg = isNeg;
        Sum.Simplify();
        return Sum;
    }

    public SciDec Times (SciDec RightDec) {
        return Times(this, RightDec);
    }

    public static SciDec IntegerDivide (SciDec LeftDec, SciDec RightDec) throws RationalNumberException{
        if(RightDec.length() == 1 && RightDec.charAt(0) == '0') {throw new RationalNumberException();}
        if(Equals(LeftDec, RightDec)) return new SciDec("1",0);
        if(LeftDec.length() == 1 && LeftDec.charAt(0) == '0') return new SciDec("0",0,false);
        //if(RightDec.NoSignMoreThan(LeftDec)) return new SciDec("0",0);
        //LeftDec.Simplify(); RightDec.Simplify();
        SciDec L = LeftDec.Copy(), R = RightDec.Copy(), RO = R.Copy(), Result = Zero.Copy();
        //L.Simplify(-3); R.Simplify(-3);
        
        Boolean isNeg = LeftDec.isNeg ^ RightDec.isNeg;
        L.isNeg = false; R.isNeg = false;
        L.Simplify(); R.Simplify();
        // while(!R.MoreThan(L)) {L = L.Subtract(R); //L.Simplify();
        // Counter = Counter.Add(One);}
        // return Counter;
        int IntCounter = 0, ResultCounter = 0;
        while(R.MoreThan(L)){L.index++; ResultCounter++;}
        
        //if (L.length()<=R.length()) L.toScience(L.index-R.length()+L.length()-1);
        
        while(L.MoreThan(RO)) {RO.AddIndex();}
        if(RO.MoreThan(L)) {RO.index--;}
        //L.Read(); RO.Read();
        {
            IntCounter = 0;
            while(L.MoreThan(RO)) {L = L.Subtract(RO); IntCounter++;}
            //System.out.println(IntCounter);
            
            Result = Result.Add(new SciDec(new StringBuffer(Integer.toString(IntCounter)), L.index - R.index, false));
            //Result.Read();
        }
        //L.Read(); RO.Read(); System.out.println("nobug");
        int IndexCounter = L.index - R.index;
        while(L.MoreThan(R)){
            IntCounter = 0;
            while(L.MoreThan(RO)) {L=L.Subtract(RO); IntCounter++;}
            Result = Result.Add(new SciDec(new StringBuffer(Integer.toString(IntCounter)), IndexCounter));
            while(!L.MoreThan(RO)){RO.index--; IndexCounter--;}
        }
        Result.isNeg = isNeg;
        Result.index -= ResultCounter;
        Result.Simplify();
        return Result;
    }

    public SciDec IDivide (SciDec RightDec) throws RationalNumberException {
        return IntegerDivide(this, RightDec);
    }

    public static SciDec Mod (SciDec LeftDec, SciDec RightDec) throws RationalNumberException{
        if(Equals(LeftDec, RightDec)) return new SciDec("0",0);
        if(!LeftDec.MoreThan(RightDec)) return LeftDec;
        SciDec Mod_Result = Subtract(LeftDec, Times(IntegerDivide(LeftDec, RightDec), RightDec));
        return Mod_Result;
    }

    public SciDec Mod (SciDec RightDec) throws RationalNumberException {
        return Mod(this, RightDec);
    }

    public static SciDec gcd (SciDec LeftDec, SciDec RightDec) throws RationalNumberException{
        SciDec L = LeftDec.Copy(), R = RightDec.Copy(), T; L.Simplify(); R.Simplify();
        while (!R.Equals(Zero)) {L = L.Mod(R);
            T = L.Copy(); L = R.Copy(); R = T.Copy();}
        return L;
    }

    public static SciDec IntegerPart (SciDec Dec) {
        SciDec D = Dec.Copy();
        D.toScience(0);
        if(D.DotFound()) D.delete(D.DotIndex(), D.length());
        return D;
    }

    public static SciDec Floor (SciDec Dec) {
        SciDec D = Dec.Copy(), Result = Zero.Copy();
        D.Simplify(); D.RemoveDot();
        while(D.MoreThan(One) || D.Equals(One)) {D = D.Subtract(One); D.RemoveDot(); Result = Result.Add(One); //D.Read(); Result.Read();
        }
        while(Zero.MoreThan(D)) {D = D.Add(One); D.RemoveDot(); Result = Result.Subtract(One); //D.Read(); Result.Read();
        }
        return Result;
    }//Floor算法有问题，待更正

    public static SciDec FractionalPart (SciDec Dec) {
        return Subtract(Dec, Floor(Dec));
    }

    public static SciDec Negative (SciDec Dec) {
        SciDec D = Dec.Copy();
        D.isNeg = !D.isNeg;
        return D;
    }

    public static boolean NoSignEquals (SciDec LeftDec, SciDec RightDec) {//only allows two positive numbers to compare
        if(LeftDec.index == RightDec.index && LeftDec.FullDec == RightDec.FullDec) return true;
        else return false;}

    public static boolean Equals (SciDec LeftDec, SciDec RightDec) {
        LeftDec.Simplify(); RightDec.Simplify();
        if(LeftDec.index == RightDec.index && LeftDec.FullDec == RightDec.FullDec && !(LeftDec.isNeg ^ RightDec.isNeg)) return true;
        else return false;}

    public boolean Equals (SciDec RightDec) {
        return Equals(this, RightDec);
    }

    public static boolean NoSignLeftMoreThanRight (SciDec LeftDec, SciDec RightDec) {//only allows two positive numbers to compare
        if(LeftDec.Equals(Zero)) return false;
        if(!LeftDec.Equals(Zero) && RightDec.Equals(Zero)) return true;
        StringBuffer TempStringLeft = new StringBuffer(); TempStringLeft.append(LeftDec.FullDec);
        StringBuffer TempStringRight = new StringBuffer(); TempStringRight.append(RightDec.FullDec);
        //System.out.println("1. "+TempStringLeft+" "+TempStringRight);
        int offset, index = 0;
        if(LeftDec.index > RightDec.index) {offset = LeftDec.index - RightDec.index;
            for(int i = 0; i < offset; i++) {TempStringLeft.append('0');}}
        else if (LeftDec.index < RightDec.index) {offset = RightDec.index - LeftDec.index;
            for(int i = 0; i < offset; i++) {TempStringRight.append('0');}}
        //System.out.println("2. "+TempStringLeft+" "+TempStringRight);
        if(TempStringLeft.length() > TempStringRight.length()) return true;
        else if (TempStringLeft.length() < TempStringRight.length()) return false;
        else {while(index < TempStringLeft.length()) {if((int)TempStringLeft.charAt(index) > (int)TempStringRight.charAt(index)) return true;
            else if(TempStringLeft.charAt(index) == TempStringRight.charAt(index)) {index++;}
            else return false;} return false;}
    }

    public static boolean LeftMoreThanRight (SciDec LeftDec, SciDec RightDec) {
        if(!LeftDec.isNeg && RightDec.isNeg) return true;
        else if(LeftDec.isNeg && !RightDec.isNeg) return false;
        else if(!LeftDec.isNeg && !RightDec.isNeg) return NoSignLeftMoreThanRight(LeftDec, RightDec);
        else return !(NoSignEquals(LeftDec, RightDec) || NoSignLeftMoreThanRight(LeftDec, RightDec));
    }

    public boolean NoSignMoreThan (SciDec RightDec) {
        return NoSignLeftMoreThanRight(this, RightDec);
    }

    public boolean MoreThan (SciDec RightDec) {
        return LeftMoreThanRight(this, RightDec);
    }

    public boolean DotFound () {
        int temp = 0;
        for(; temp < this.length(); temp++) {
            if(this.charAt(temp) == '.') return true;
        }
        return false;
    }

    public int DotIndex (StringBuffer Str) {
        int temp = 0;
        for(; temp < Str.length(); temp++) {
            if(Str.charAt(temp) == '.') return temp;
        }
        return temp;
    }

    public int DotIndex () {
        int temp = this.length() - 1;
        for(int i = 0; i < temp; i++) {
            if(this.charAt(i) == '.') return i;}
        return temp;
    }

    public void toScience () {
        int temp = this.length() - 1;
        this.Simplify();
        if(this.length() > 1) {this.index += this.length() - 1; this.insert(1,'.');}
        this.Simplify();
    }

    public void toScience (int index) {
        this.Simplify();
        int temp = this.index - index, length = this.length(), DotIndex = this.DotIndex(); boolean DotFound = this.DotFound();
        //System.out.println("String info "+this.index+" "+temp+" "+length+" "+DotIndex+" "+DotFound);
        this.index = index;
        if (temp > 0) {
            if(DotFound) {if(length - DotIndex - 1 >= temp) {this.insert(DotIndex + temp + 1, '.'); this.delete(DotIndex, DotIndex+1);}
                          else {this.delete(DotIndex, DotIndex+1); for(int i = 0; i < temp - (length - 1 - DotIndex); i++) this.append('0'); this.Read();}}
            else for(int i = 0; i < temp; i++) this.append('0');}
        else if (temp < 0) {
            temp = -temp;
            if(DotFound) {if(temp <= DotIndex) {this.delete(DotIndex, DotIndex + 1); this.insert(DotIndex - temp,'.');}
                          else {this.delete(DotIndex, DotIndex + 1); for(int i = 0; i < temp - DotIndex; i++) this.insert(0, '0'); this.insert(0, '.');}}
            else {if(temp > length) {for(int i = 0; i < temp - length; i++) this.insert(0, '0'); this.insert(0, '.');}
                  else {insert(length - temp, '.');}}
        }
    }

    public void RemoveDot () {
        for(int temp = 0; temp < this.length(); temp++) {if(this.charAt(temp) == '.')
        {this.index -= this.length() - temp - 1; this.delete(temp, temp + 1); break;}}
        this.Simplify();
    }

    public static void Simplify (StringBuffer Str) {
        Decimal.DecimalSimplify(Str);
    }

    public void Simplify () {
        if(this.length() == 1 && this.charAt(0) == '0') this.isNeg = false;
        //while(this.length() > 1 && this.charAt(this.length() - 1) == '0') {this.delete(this.length() - 1, this.length()); this.index++;}
        Decimal.DecimalSimplify(this.FullDec);
    }

    public void Simplify (int Style) {
        switch(Style){

            case  0:if(this.length() == 1 && this.charAt(0) == '0') this.isNeg = false; break;
            case -1:while(this.length() > 1 && this.charAt(this.length() - 1) == '0') {this.delete(this.length() - 1, this.length()); this.index++;} break;
            case -2:while(this.length() > 1 && this.charAt(0) == '0' && this.charAt(1) != '.') {this.delete(0, 1);} break;
            case -3:while(this.length() > 1 && this.charAt(this.length() - 1) == '0') {this.delete(this.length() - 1, this.length()); this.index++;}
                    while(this.length() > 1 && this.charAt(0) == '0' && this.charAt(1) != '.') {this.delete(0, 1);} break;
            
            case  1:this.RemoveDot(); break;
            case  2:Decimal.DecimalSimplify(this.FullDec); break;
            case  3:this.toScience(0); break;
            case  4:if(this.length() == 1 && this.charAt(0) == '0') this.isNeg = false;
                    while(this.length() > 1 && this.charAt(this.length() - 1) == '0') {this.delete(this.length() - 1, this.length()); this.index++;}
                    Decimal.DecimalSimplify(this.FullDec);
                    break;
            case  5:while(this.index > 0) {this.append('0'); this.index--;} break;
        }
    }

    public Decimal toDecimal () throws RealDecimalNumberException {
        this.toScience(0);
        Decimal D = new Decimal(this.FullDec);
        if(this.isNeg) {D.DecimalForm.insert(0,'-');}
        this.RemoveDot();
        return D; 
    }

    public SciDec Copy() {
        return new SciDec(this.FullDec, this.index, this.isNeg);
    }

    public StringBuffer getInfo () {
        StringBuffer Info = new StringBuffer();
        if(this.isNeg)Info.append('-');
        Info.append(this.FullDec);
        if(this.index != 0){Info.append(" E ");
        Info.append(String.valueOf(this.index));}
        return Info;
    }

}

class Rational {
    SciDec Numerator;
    SciDec Denominater;
    int index;//default as 0
    boolean isNeg;

    public SciDec Numerator() {
        return this.Numerator;
    }

    public SciDec Denominator() {
        return this.Denominater;
    }

    public Rational (SciDec Num, SciDec Denom) throws RationalNumberException {
        SciDec N = Num.Copy(), D = Denom.Copy();
        N.Simplify(); D.Simplify();
        N.toScience(0); D.toScience(0);
        N.RemoveDot(); D.RemoveDot();
        N.Read(); D.Read();
        this.isNeg = N.isNeg ^ D.isNeg;
        this.index = N.index - D.index;
        N.isNeg = false; D.isNeg = false;

        N.index = 0; D.index = 0;
        if(this.index > 0) N.index += this.index;
        else if(this.index < 0) D.index += -this.index;
        N.toScience(0); D.toScience(0);
        this.index = 0;
        N.Read(); D.Read();
        
        SciDec G = SciDec.gcd(N,D);
        G.Read();
        G.toScience(0);
        G.RemoveDot();
        N = N.IDivide(G); D = D.IDivide(G);
        //System.out.println(this.index);
        //N.Read(); D.Read();
        this.Numerator = N; this.Denominater = D;
    }

    public Rational (SciDec Num, SciDec Denom, boolean Simplify) throws RationalNumberException {
        if(Simplify) {
            SciDec N = Num.Copy(), D = Denom.Copy();
            N.Simplify(); D.Simplify();
            N.toScience(0); D.toScience(0);
            N.RemoveDot(); D.RemoveDot();
            //N.Read(); D.Read();
            this.isNeg = N.isNeg ^ D.isNeg;
            this.index = N.index - D.index;
            N.isNeg = false; D.isNeg = false;
    
            N.index = 0; D.index = 0;
            if(this.index > 0) N.index += this.index;
            else if(this.index < 0) D.index += -this.index;
            N.toScience(0); D.toScience(0);
            this.index = 0;
            
            SciDec G = SciDec.gcd(N,D);
            //G.Read();
            G.toScience(0);
            G.RemoveDot();
            N = N.IDivide(G); D = D.IDivide(G);
            //System.out.println(this.index);
            //N.Read(); D.Read();
            this.Numerator = N; this.Denominater = D;}
        else {
            SciDec N = Num.Copy(), D = Denom.Copy();
            N.Simplify(); D.Simplify();
            this.isNeg = N.isNeg ^ D.isNeg;
            this.index = N.index - D.index;
            N.isNeg = false; D.isNeg = false;
            N.index = 0; D.index = 0;

            this.Numerator = N; this.Denominater = D;}
    }

    public Rational (String Num, String Denom, boolean Simplify) throws RationalNumberException, RealDecimalNumberException {
        SciDec N = new SciDec(Num), D = new SciDec(Denom);
        //N.Read();
        if(Simplify) {
            N.Simplify(); D.Simplify();
            N.toScience(0); D.toScience(0);
            N.RemoveDot(); D.RemoveDot();
            N.Read(); D.Read();
            this.isNeg = N.isNeg ^ D.isNeg;
            this.index = N.index - D.index;
            N.isNeg = false; D.isNeg = false;
    
            N.index = 0; D.index = 0;
            if(this.index > 0) N.index += this.index;
            else if(this.index < 0) D.index += -this.index;
            N.toScience(0); D.toScience(0);
            this.index = 0;
            
            SciDec G = SciDec.gcd(N,D);
            //G.Read();
            G.toScience(0);
            G.RemoveDot();
            N = N.IDivide(G); D = D.IDivide(G);
            //System.out.println(this.index);
            //N.Read(); D.Read();
            this.Numerator = N; this.Denominater = D;}
        else {
            N.Simplify(); D.Simplify();
            this.isNeg = N.isNeg ^ D.isNeg;
            this.index = N.index - D.index;
            N.isNeg = false; D.isNeg = false;
            N.index = 0; D.index = 0;

            this.Numerator = N; this.Denominater = D;}
    }

    public Rational (String Num, String Denom) throws RationalNumberException, RealDecimalNumberException {
        this(Num, Denom, true);
    }

    public void Read() {
        if(this.isNeg)System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec+"*10^"+this.index);
        else System.out.println(Numerator.FullDec+"//"+Denominater.FullDec+"*10^"+this.index);
    }

    public void Read(int Style) {
        switch(Style){
        case 0: if(this.isNeg) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec+"*10^"+this.index);
                else System.out.println(Numerator.FullDec+"//"+Denominater.FullDec+"*10^"+this.index); break;
        case 1: if(this.isNeg) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index);
                else System.out.println(Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index); break;
        case 2: if(this.isNeg && this.index != 0) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index);
                else if(this.index != 0) System.out.println(Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index);
                else if(this.isNeg) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec);
                else System.out.println(Numerator.FullDec+"//"+Denominater.FullDec); break;
        case 3: if(this.Numerator.length() == 1 && this.Numerator.charAt(0) == '0'
                && !(this.Denominater.length() == 1 && this.Denominater.charAt(0) == '0')) System.out.println('0');
                else if(this.Denominater.length() == 1 && this.Denominater.charAt(0) == '1'){
                    if(this.isNeg) System.out.println("-"+this.Numerator.FullDec);
                    else System.out.println(this.Numerator.FullDec);}
                else{
                    if(this.isNeg && this.index != 0) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index);
                    else if(this.index != 0) System.out.println(Numerator.FullDec+"//"+Denominater.FullDec+"E"+this.index);
                    else if(this.isNeg) System.out.println("-"+Numerator.FullDec+"//"+Denominater.FullDec);
                    else System.out.println(Numerator.FullDec+"//"+Denominater.FullDec); break;}
        }
    }

    public void Simplify() throws RationalNumberException {
        SciDec G = SciDec.gcd(this.Numerator, this.Denominater);
        this.Numerator = this.Numerator.IDivide(G); this.Denominater = this.Denominater.IDivide(G);
        //this.Numerator.toScience(0); this.Denominater.toScience(0);
        //if(this.index > 0) this.Numerator.index += this.index;
        //else if(this.index < 0) this.Denominater.index += -this.index;
        //this.Numerator.toScience(0); this.Denominater.toScience(0);
        //System.out.println("Successfully Simplified");
    }

    public void Simplify(int Style) throws RationalNumberException {
        switch(Style){
            case 0: {SciDec G = SciDec.gcd(this.Numerator, this.Denominater);
                    this.Numerator = this.Numerator.IDivide(G); this.Denominater = this.Denominater.IDivide(G);
                    this.Numerator.toScience(0); this.Denominater.toScience(0);
                    if(this.index > 0) this.Numerator.index += this.index;
                    else if(this.index < 0) this.Denominater.index += -this.index;
                    this.Numerator.toScience(0); this.Denominater.toScience(0);
                    this.index = 0;
                    //System.out.println("Successfully Simplified");
                    break;}
            case 1: {if(this.index > 0) this.Numerator.index += this.index;
                    else if(this.index < 0) this.Denominater.index += -this.index;
                    this.Numerator.toScience(0);
                    //this.Numerator.Read();
                    this.Denominater.toScience(0);
                    //this.Denominater.Read();
                    //System.out.println("Successfully Simplified");
                    this.index = 0;
                    break;}
            case 2: {SciDec G = SciDec.gcd(this.Numerator, this.Denominater);
                    this.Numerator = this.Numerator.IDivide(G); this.Denominater = this.Denominater.IDivide(G);
                    this.Numerator.toScience(0); this.Denominater.toScience(0); break;}
            //case 3: 
    }}

    public static Rational Add (Rational LeftR, Rational RightR) throws RationalNumberException {
        System.out.println("Add Left = "+LeftR.Numerator.FullDec+"/"+LeftR.Denominater.FullDec+" Right = "+RightR.Numerator.FullDec+"/"+RightR.Denominater.FullDec);
        SciDec N = SciDec.Add(LeftR.Numerator.Times(RightR.Denominater),RightR.Numerator.Times(LeftR.Denominater));
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        System.out.println("Add Result Num = "+N.getInfo()+" Denom = "+D.getInfo());
        return new Rational(N,D);
    }

    public static Rational Add (Rational LeftR, Rational RightR, boolean Simplify) throws RationalNumberException {
        SciDec N = SciDec.Add(LeftR.Numerator.Times(RightR.Denominater),RightR.Numerator.Times(LeftR.Denominater));
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        return new Rational(N,D,Simplify);
    }

    public Rational Add (Rational RightR) throws RationalNumberException {
        return Add(this, RightR);
    }

    public static Rational Subtract (Rational LeftR, Rational RightR) throws RationalNumberException {
        System.out.println("Subtract Left = "+LeftR.Numerator.FullDec+"/"+LeftR.Denominater.FullDec+" Right = "+RightR.Numerator.FullDec+"/"+RightR.Denominater.FullDec);
        SciDec N = SciDec.Subtract(LeftR.Numerator.Times(RightR.Denominater),RightR.Numerator.Times(LeftR.Denominater));
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        System.out.println("Subtract Result Num = "+N.getInfo()+" Denom = "+D.getInfo());
        return new Rational(N,D);
    }

    public static Rational Subtract (Rational LeftR, Rational RightR, boolean Simplify) throws RationalNumberException {
        SciDec N = SciDec.Subtract(LeftR.Numerator.Times(RightR.Denominater),RightR.Numerator.Times(LeftR.Denominater));
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        return new Rational(N,D,Simplify);
    }

    public Rational Subtract (Rational RightR) throws RationalNumberException {
        return Subtract(this, RightR);
    }

    public static Rational Times (Rational LeftR, Rational RightR) throws RationalNumberException {
        System.out.println("Times Left = "+LeftR.Numerator.FullDec+"/"+LeftR.Denominater.FullDec+" Right = "+RightR.Numerator.FullDec+"/"+RightR.Denominater.FullDec);
        SciDec N =SciDec.Times(LeftR.Numerator, RightR.Numerator);
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        System.out.println("Times Result Num = "+N.getInfo()+" Denom = "+D.getInfo());
        return new Rational(N,D);
    }

    public static Rational Times (Rational LeftR, Rational RightR, boolean Simplify) throws RationalNumberException {
        SciDec N = SciDec.Times(LeftR.Numerator, RightR.Numerator);
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Denominater);
        //D.Read();
        return new Rational(N,D,Simplify);
    }

    public Rational Times (Rational RightR) throws RationalNumberException {
        return Times(this, RightR);
    }

    public static Rational Divide (Rational LeftR, Rational RightR) throws RationalNumberException {
        System.out.println("Divide Left = "+LeftR.Numerator.FullDec+"/"+LeftR.Denominater.FullDec+" Right = "+RightR.Numerator.FullDec+"/"+RightR.Denominater.FullDec);
        SciDec N = SciDec.Times(LeftR.Numerator, RightR.Denominater);
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Numerator);
        //D.Read();
        System.out.println("Divide Result Num = "+N.getInfo()+" Denom = "+D.getInfo());
        return new Rational(N,D);
    }

    public static Rational Divide (Rational LeftR, Rational RightR, boolean Simplify) throws RationalNumberException {
        SciDec N = SciDec.Times(LeftR.Numerator, RightR.Denominater);
        //N.Read();
        SciDec D = SciDec.Times(LeftR.Denominater, RightR.Numerator);
        //D.Read();
        return new Rational(N,D,Simplify);
    }

    public Rational Divide (Rational RightR) throws RationalNumberException {
        return Divide(this, RightR);
    }

    public static boolean MoreThan (Rational LeftR, Rational RightR) {
        if(!LeftR.isNeg && !RightR.isNeg) return SciDec.NoSignLeftMoreThanRight(SciDec.Times(LeftR.Numerator, RightR.Denominater),
        SciDec.Times(LeftR.Denominater, RightR.Numerator));
        else if(LeftR.isNeg && !RightR.isNeg) return false;
        else if(!LeftR.isNeg && RightR.isNeg) return true;
        else return SciDec.NoSignLeftMoreThanRight(SciDec.Times(LeftR.Denominater, RightR.Numerator),
        SciDec.Times(LeftR.Numerator, RightR.Denominater));
    }

    public boolean MoreThan (Rational RightR) {
        return MoreThan(this, RightR);
    }

    public static boolean MoreThanOne (Rational Rational) {
        if(!Rational.isNeg && SciDec.LeftMoreThanRight(Rational.Numerator, Rational.Denominater)) return true;
        else return false;
    }
    
    public boolean MoreThanOne () {
        return MoreThanOne(this);
    }

    public static boolean Equals (Rational LeftR, Rational RightR) throws RationalNumberException {
        LeftR.Simplify(); RightR.Simplify();
        return (SciDec.Equals(LeftR.Numerator, RightR.Numerator)) && (SciDec.Equals(LeftR.Denominater, RightR.Denominater));
    }

    public boolean Equals (Rational RightR) throws RationalNumberException {
        return Equals(this, RightR);
    }

    public static Rational Floor (Rational Rational) throws RationalNumberException, RealDecimalNumberException {
        Rational R = Rational.Copy(), One = new Rational(SciDec.One.Copy(), SciDec.One.Copy()); SciDec Result = SciDec.Zero.Copy();
        R.Simplify();
        while(MoreThanOne(R) || R.Equals(One)) {R.Numerator = R.Numerator.Subtract(R.Denominater); Result = Result.Add(SciDec.One); //R.Read(2); Result.Read();
        }
        while(R.isNeg) {R = R.Add(One); Result = Result.Subtract(SciDec.One);// R.Read(); Result.Read();
        }
        return new Rational(Result, SciDec.One.Copy());
    }

    public static Rational Mod (Rational LeftR, Rational RightR) throws RationalNumberException, RealDecimalNumberException {
        if(Equals(LeftR, RightR)) return new Rational(SciDec.Zero.Copy(), SciDec.One.Copy());
        if(RightR.Equals(new Rational(SciDec.One.Copy(), SciDec.One.Copy()))) throw new RationalNumberException();
        Rational Mod_Result = Subtract(LeftR, Times(Floor(Divide(LeftR, RightR)), RightR));
        return Mod_Result;
    }

    public static Rational FractionalPart (Rational Rational) throws RationalNumberException, RealDecimalNumberException {
        return Subtract(Rational, Floor(Rational));
    }

    public Rational Copy() throws RationalNumberException, RealDecimalNumberException {
        Rational R = new Rational("0","1");
        R.Numerator = this.Numerator;
        R.Denominater = this.Denominater;
        R.index = this.index;
        R.isNeg = this.isNeg;
        return R;
    }

}

public class Decimal implements Constants{
    public StringBuffer DecimalForm;
    //public static StringBuffer LeftDecimal = new StringBuffer("");
    //public static StringBuffer RightDecimal = new StringBuffer("");

    public boolean DotFound () {
        int temp = 0;
        for(; temp < this.length(); temp++) {
            if(this.charAt(temp) == '.') return true;
        }
        return false;
    }

    public static int DotIndex (StringBuffer Str) {
        int temp = 0;
        for(; temp < Str.length(); temp++) {
            if(Str.charAt(temp) == '.') return temp;
        }
        return temp;
    }

    public int DotIndex () {
        return DotIndex(this.DecimalForm);
    }

    public int length () {
        return this.DecimalForm.length();
    }

    public char charAt (int index) {
        return this.DecimalForm.charAt(index);
    }

    public void Read () {
        System.out.println(this.DecimalForm);
    }

    public Decimal (String Dec) throws RealDecimalNumberException {
        if(RealDecimalNumberQ(new StringBuffer(Dec))) this.DecimalForm = new StringBuffer(Dec);
        else throw new RealDecimalNumberException();
    }

    public Decimal (StringBuffer Dec) throws RealDecimalNumberException {
        if(RealDecimalNumberQ(Dec)) this.DecimalForm = Dec;
        else throw new RealDecimalNumberException();
    }

    public static Scanner Inputter = new Scanner(System.in);

    public static boolean RealDecimalNumberQ (StringBuffer toTest) {
        boolean FoundSignal = false, FoundDot = false;
        int temp = 0;
        if(toTest.charAt(0) == '+' || toTest.charAt(0) == '-') {
            temp++;
            FoundSignal = true;
            while (temp < toTest.length()) {
                if(48<=(int)toTest.charAt(temp) && (int)toTest.charAt(temp)<=59) {temp++;}
                else if (toTest.charAt(temp) == '.' && !FoundDot) {FoundDot = true; temp++;}
                else return false;
            }
        return true;}
        else {while (temp < toTest.length()) {
            if(48<=(int)toTest.charAt(temp) && (int)toTest.charAt(temp)<=59) {temp++;}
            else if (toTest.charAt(temp) == '.' && !FoundDot) {FoundDot = true; temp++;}
            else return false;
        } return true;}
    }
    
    /*public static void setLeftDecimal (StringBuffer ld) {
        if (RealDecimalNumberQ(ld))
        LeftDecimal = ld;
        else System.out.println("Line wrong.");}

    public static void setLeftDecimal () {
        StringBuffer op = new StringBuffer(Inputter.nextLine());
        if (RealDecimalNumberQ(op))
        LeftDecimal = op;
        else System.out.println("Line wrong.");
    }

    public static void setRightDecimal (StringBuffer ld) {
        if (RealDecimalNumberQ(ld))
        RightDecimal = ld;
        else System.out.println("Line wrong.");}

    public static void setRightDecimal () {
        StringBuffer op = new StringBuffer(Inputter.nextLine());
        if (RealDecimalNumberQ(op))
        RightDecimal = op;
        else System.out.println("Line wrong.");}*/
    
    public static void DecimalSimplify (StringBuffer Decimal) {
            int DeleteStart = 0, DeleteEnd = 0, DotIndex = Decimal.length(), temp = 0;
            boolean DotFound = false;
            if(Decimal.length() > 1){
            if(Decimal.charAt(0) == '+') {Decimal.delete(0,1); DotIndex--;}//移除正号
            while(temp < Decimal.length()) {if(Decimal.charAt(temp) == '.') {DotFound = true; DotIndex = temp; //System.out.println("Dot is at "+DotIndex);
             break;} else temp++; }
        
            if(Decimal.charAt(0) == '-') {DeleteStart = 1; DeleteEnd = 1;}
            //System.out.print("Dot is at "+DotIndex+"\n");
            if(DotIndex == 0 || (Decimal.charAt(0) == '-' && DotIndex == 1)) {Decimal.insert(DotIndex,"0"); DotIndex++;}//填充0
            if((DotFound && DotIndex > DeleteStart) || !DotFound){temp = DeleteStart; //System.out.println("now temp is "+temp);
            while(temp < DotIndex && temp < Decimal.length() - 1) {if(Decimal.charAt(temp) == '0' && Decimal.charAt(temp+1) != '.') {temp++; //System.out.println("now temp is "+temp);
                } else {break;} //System.out.println("now temp is "+temp);
            } DeleteEnd = temp; 
            Decimal.delete(DeleteStart, DeleteEnd); //System.out.println("Deleted "+DeleteStart+" "+DeleteEnd)
            ;}//删除前置0
        
            //System.out.println("Now "+Decimal);

            if(DotFound) while(temp < Decimal.length()) {if(Decimal.charAt(temp) == '.') { //System.out.println("Dot is at "+DotIndex);
            break;} else temp++; } DotIndex = temp;//Reload DotIndex
            
            if(DotFound && (Decimal.charAt(Decimal.length() - 1) == '0' || Decimal.charAt(Decimal.length() - 1) == '.')) {temp = Decimal.length() - 1;
                DeleteEnd = Decimal.length();
                while(Decimal.charAt(temp) == '0') {temp--;}
                DeleteStart = temp + 1;
                if(Decimal.charAt(temp) == '.') DeleteStart--;
            Decimal.delete(DeleteStart, DeleteEnd + 1);
            //System.out.println("Deleted "+DeleteStart+" "+DeleteEnd)
            ;}//删除后置0
        
            if(Decimal.charAt(Decimal.length() - 1) == '.') Decimal.delete(Decimal.length()-1, Decimal.length());
        
            if(Decimal.length() == 2 && Decimal.charAt(0) == '-' && Decimal.charAt(1) == '0') Decimal.delete(0, 1);}
        
            if(Decimal.length() == 1 && (Decimal.charAt(0) == '-' || Decimal.charAt(0) == '.')) {Decimal.delete(0,1); Decimal.append("0");}
        
        }
        
    public static void DecimalSimplify (Decimal Dec) {
            DecimalSimplify(Dec.DecimalForm);
        }

    public void DecimalSimplify () {
            DecimalSimplify(this.DecimalForm);
        }



    public static void main (String[] args) throws RealDecimalNumberException, RationalNumberException {
        /*StringBuffer n = new StringBuffer("");
        while(true) {
            n = new StringBuffer(Inputter.nextLine());
            DecimalSimplify(n);
            if(RealDecimalNumberQ(n)) {System.out.println(n);}
        }*/
        // SciDec S1 = new SciDec("5"), S2 = new SciDec("1.123");
        // S1.Read(); S2.Read();
        // SciDec S3 = SciDec.IntegerDivide(S1, S2), S4 = SciDec.Mod(S1, S2); 
        // S3.Read(); S4.Read();

        Rational R1 = new Rational("1.5","1");//, R2 = new Rational("1", "2.1");
        // // Rational Zero = new Rational("0", "1"), One = new Rational("1", "1");
        R1.Read(2);// R2.Read(2);
        // Rational R3 = Rational.Add(R1, R2), R4 = Rational.Subtract(R1, R2); R3.Read(2); R4.Read(2);
        // Rational R5 = Rational.Times(R1, R2), R6 = Rational.Divide(R1, R2); R5.Read(2); R6.Read(2);
        // //Rational R7 = Rational.Subtract(R2, R1); R7.Read(2); System.out.println(Rational.MoreThan(Zero, One));
        // Rational R8 = Rational.Times(R2,Rational.Floor(Rational.Divide(R1, R2))), R9 = Rational.Mod(R1,R2); R1.Read(); R8.Read(2); R9.Read(2);
        //注：Rational的index构造器有问题，主要发生于gcd函数。gcd函数需要重新改写算法

        /*SciDec S1 = new SciDec("10.1"), S2 = new SciDec("0.1");
        S1.RemoveDot(); S2.RemoveDot();
        S1.Read(); S2.Read();
        SciDec S = SciDec.IntegerDivide(S1, S2);
        S.Read();*/

        /*SciDec S1 = new SciDec("10.5"), S2 = new SciDec("-0.3"); S1.Read(); S2.Read(); //S1.RemoveDot(); S2.RemoveDot();
        SciDec S3 = SciDec.Floor(S2); SciDec S4 = SciDec.FractionalPart(S1); S3.Read(); S4.Read();*/
        
    }
}

