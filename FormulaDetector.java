package FormulaDetector;
import java.util.Scanner;

public class FormulaDetector{








    public static double Times (double leftIn, double rightIn) {
        return leftIn * rightIn;}





    static char[] Decimal = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    static char LeftParenthese = '(';
    static char RightParenthese = ')';
    static char[] BO1 = {'*', '/', '%'};
    static char[] BO2 = {'+', '-'};
     //允许存在的字符集


    public static int index          = 0;
    public static int temp           = 0;
    public static int FocusLeft      = 0;
    public static int FocusRight     = 0;
    public static int BOindex        = 0;
    public static int NumStart       = 0;
    public static int NumEnd         = 0;
    public static int BOLvl          = 1;
    public static boolean LeftFound  = false;
    public static boolean RightFound = false;
    public static boolean FocusFound = false;
    public static boolean BOFound    = false;
    public static boolean NumFound   = false;
    public static boolean DotFound   = false;
    public static boolean SignFound  = false;
    public static boolean SystemBug  = false;

    /*public static int ptBoolean (boolean b) {
        if (b) return 1; else return 0;
    }*/
    
    public static boolean isParentheses (char c) {
        if(c == LeftParenthese || c == RightParenthese) return true;
        else return false;
    }

    public static boolean isFirstBO (char c) {
        if (c == '*' || c == '/' || c == '%') return true;
        else return false;
    }

    public static boolean isZerothBO (char c) {
        if (c == '^') return true;
        else return false;
    }

    public static boolean isSecondBO (char c) {
        if (c == '+' || c == '-') return true;
        else return false;
    }

    public static boolean isBO (char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') return true;
        else return false;
    }

    public static boolean isNumber (char c) {
        temp = (int)c;
        if (48 <= temp && temp <= 59) return true;
        else return false;
    }

    public static boolean isPartOfNumber (StringBuffer Str, int loc) {
        if(isNumber(Str.charAt(loc))) return true;
        else if(Str.charAt(loc) == '.'
            &&((loc > 0 && isNumber(Str.charAt(loc-1)))
            ||(loc < Str.length() - 1 && isNumber(Str.charAt(loc+1))))) {/*System.out.println("loc is "+loc);*/ return true;}
        else return false;
    }

    public static boolean isSignal (StringBuffer Str, int loc) {
        if(!isSecondBO(Str.charAt(loc))) return false;
        else if(isSecondBO(Str.charAt(loc)) && loc == 0 && (isPartOfNumber(Str, 1) || Str.charAt(1) == LeftParenthese)) return true;
        else if(isSecondBO(Str.charAt(loc)) && loc < Str.length() - 1 && loc > 0) {
            if (isNumber(Str.charAt(loc - 1))) return false;
            else if (loc != Str.length() - 1 && isBO(Str.charAt(loc - 1)) && !isBO(Str.charAt(loc + 1))) return true;
            else if (isPartOfNumber(Str, loc+1) || Str.charAt(loc - 1) == LeftParenthese) return true;
            else return false;
        }
        else return false;
    }

    public static void FindInnerParentheses (StringBuffer Str) {
        index = 0;
        FocusLeft = 0;
        FocusRight = 0;
        LeftFound = false;
        RightFound = false;
        while(index < Str.length()){
            if(Str.charAt(index) == LeftParenthese) {FocusLeft = index; LeftFound = true;}
            if(Str.charAt(index) == RightParenthese && LeftFound) {FocusRight = index + 1; RightFound = true; break;}
            else if(Str.charAt(index) == RightParenthese && !LeftFound) {System.out.println("Parentheses unmatched at "+index); break;}
            index++;}
        if(!LeftFound && !RightFound) {FocusRight = 0;}
        else if (LeftFound) FocusRight = index;
    }

    public static void FindFunctions (StringBuffer StrFocus) {
//need to update
    }

    public static void FindBinaryOperators (StringBuffer StrFocus) {
        if(StrFocus.length() > 2){
        index = 1;
        BOLvl = 1;
        BOFound = false;
        while(index < StrFocus.length() - 1){
            /* System.out.println("checking "+index); */ 
            if( isZerothBO(StrFocus.charAt(index))
                && !isBO(StrFocus.charAt(index-1))){
                if(index != StrFocus.length() - 2 && isSignal(StrFocus, index + 1))
                    {BOFound = true; BOindex = index; BOLvl = 3; break;}
                else if(isPartOfNumber(StrFocus,index + 1))
                    {BOFound = true; BOindex = index; BOLvl = 3; break;}
                }
            index++;
        }
        if(BOLvl == 1) {
            index = 1;
            while(index < StrFocus.length() - 1){
                /* System.out.println("checking "+index); */
            if(isFirstBO(StrFocus.charAt(index))
                &&!isBO(StrFocus.charAt(index-1))){
                if(index != StrFocus.length() - 2 && isSignal(StrFocus, index + 1))
                    {/*System.out.println("Followed the pattern *+/-");*/BOFound = true; BOindex = index; BOLvl = 2; break;}
                else if(isPartOfNumber(StrFocus,index + 1))
                    {/*System.out.println("Followed the pattern *2/*.");*/BOFound = true; BOindex = index; BOLvl = 2; break;}
                }
            index++;
        }}
        if(BOLvl == 1) {
            index = 1;
            while(index < StrFocus.length() - 1){
                /* System.out.println("checking "+index); */
                if(isSecondBO(StrFocus.charAt(index)) && !isSignal(StrFocus, index))
                    {BOFound = true; BOindex = index; BOLvl = 1; break;}
                index++;
            }
        }
        if(!BOFound) {index = 0;}}
    }

    public static void FindRealNumbersAfter (StringBuffer StrFocus, int startpoint) {
        index = startpoint;
        DotFound = false;
        if(isPartOfNumber(StrFocus,startpoint)) {
            NumFound = true;
            NumStart = index;
            while(index < StrFocus.length()){
                if(isNumber(StrFocus.charAt(index))) {index++;}
                else if(!DotFound && StrFocus.charAt(index) == '.') {DotFound = true; index++;}
                else {SystemBug = true; break;}
            }
            NumEnd = index;
        }
        else if(isSignal(StrFocus,startpoint)) {
            NumFound = true;
            NumStart = index;
            index ++;
            while(index < StrFocus.length()){
                if(isNumber(StrFocus.charAt(index))) {index++;}
                else if(!DotFound && StrFocus.charAt(index) == '.') {DotFound = true; index++;}
                else {SystemBug = true; break;}
            }
            NumEnd = index;
        }
        else NumFound = false;
    }

    public static void FindRealNumbersBefore (StringBuffer StrFocus, int endpoint) {
        index = endpoint;
        DotFound = false;
        NumFound = false;
        if(isPartOfNumber(StrFocus,index)) {
            NumFound = true;
            NumEnd = index + 1;
            //System.out.println("Checking index "+index);
            while(index > 0){
                if(isNumber(StrFocus.charAt(index))) {index--;}
                else if(!DotFound && StrFocus.charAt(index) == '.') {DotFound = true; index--;}
                else if(isSignal(StrFocus, index)) {index--; break;}
                else {SystemBug = true; break;}
            }
            if(index == 0) {//System.out.println("Checking 0");
                if(isNumber(StrFocus.charAt(0))) {NumStart = 0;}
                else if(!DotFound && StrFocus.charAt(0) == '.') {DotFound = true; NumStart = 0;}
                else if(isSignal(StrFocus, 0)) {NumStart = 0;}
                else if(DotFound && StrFocus.charAt(0) == '0') {SystemBug = true;}
                else NumStart = 1;}
            else {NumStart = index+1;}
        }
        else NumFound = false;
    }//need to update

    public static boolean CanBeHandled (StringBuffer StrFocus) {
        int l = StrFocus.length();
        int i = 0;
        while(i < l - 1) {
            if(isBO(StrFocus.charAt(i))&&(isFirstBO(StrFocus.charAt(i+1))||isZerothBO(StrFocus.charAt(i+1))))
            {System.out.println("Fuck. Your equation is wrong."); return false;}
            else if(isBO(StrFocus.charAt(i))&&StrFocus.charAt(i+1)==RightParenthese)
            {System.out.println("Fuck. Your equation is wrong."); return false;}
            else i++;
        }
        return true;
    }

    public static void FindFocus (StringBuffer StrEditor) {
        FindInnerParentheses(StrEditor);
        if(LeftFound && RightFound) FocusFound = true;
        else FocusFound = false;
        //System.out.println("Focus found "+FocusFound);
    }

    public static void TransferFocus (StringBuffer StrEditor, StringBuffer StrFocus) {
        if(FocusFound) {StrFocus.append(StrEditor.subSequence(FocusLeft, FocusRight+1));
        StrEditor.delete(FocusLeft, FocusRight+1);}
        else {StrFocus.append(StrEditor); StrEditor.delete(0, StrEditor.length());
            //System.out.println("Focus now");
        }
    }

    public static void Process (StringBuffer StrFocus, StringBuffer StrProcessor) {
        FindBinaryOperators(StrFocus);
        if(BOFound){
        char BO = StrFocus.charAt(BOindex);
        char sign;
        //System.out.println("Now the BO is "+BO+" at "+BOindex);
        double LeftPro = 0, RightPro = 0, Result = 0;
        FindRealNumbersAfter(StrFocus, BOindex + 1);
        if(!isSignal(StrFocus, NumStart)) RightPro = Double.parseDouble(StrFocus.substring(NumStart, NumEnd));
        else {sign = StrFocus.charAt(NumStart);
            //System.out.println("sign is "+sign);
            //System.out.println("NumStart is "+NumStart);
            //System.out.println("NumEnd is "+NumEnd);
            if(sign == '+') RightPro = Double.parseDouble(StrFocus.substring(NumStart+1, NumEnd));
            else if (sign == '-') RightPro = -Double.parseDouble(StrFocus.substring(NumStart+1, NumEnd));}
        StrFocus.delete(NumStart, NumEnd);
        //System.out.println("Now the Focus is "+StrFocus);
        StrFocus.delete(BOindex, BOindex+1);
        //System.out.println("Now the Focus is* "+StrFocus);
        FindRealNumbersBefore(StrFocus, BOindex - 1);
        //System.out.println("Found left "+NumFound);
        if(!isSignal(StrFocus, NumStart)) LeftPro = Double.parseDouble(StrFocus.substring(NumStart, NumEnd));
        else {sign = StrFocus.charAt(NumStart);
            if(sign == '+') LeftPro = Double.parseDouble(StrFocus.substring(NumStart+1, NumEnd));
            else LeftPro = -Double.parseDouble(StrFocus.substring(NumStart+1, NumEnd));}
        //System.out.println("Found left is "+LeftPro);
        StrFocus.delete(NumStart, NumEnd);
        //System.out.println("Left is "+LeftPro+" and right is "+RightPro);
        //System.out.println("Now the Focus is/ "+StrFocus);
        switch(BO) {
            case '+': Result = LeftPro + RightPro; break;
            case '-': Result = LeftPro - RightPro; break;
            case '*': Result = LeftPro * RightPro; break;
            case '/': Result = LeftPro / RightPro; break;
            case '%': Result = FundamentalFunctions.mod_re(LeftPro,RightPro); break;
            case '^': Result = FundamentalFunctions.power_pos(LeftPro, RightPro); break;
            default: break;
        }
        //System.out.println("Result is "+Result);
        StrProcessor = new StringBuffer(String.valueOf(Result));
        StrFocus.insert(NumStart, StrProcessor);
        }
    }

    public void Initialize () {
        Scanner Inputer = new Scanner(System.in);
        String Formula = Inputer.nextLine();
        StringBuffer Processor = new StringBuffer("");
        StringBuffer Processor2 = new StringBuffer("");
        StringBuffer Focus = new StringBuffer("");
        StringBuffer Editor = new StringBuffer(Formula);

        Inputer.close();

        FindFocus(Editor);
        TransferFocus(Editor, Focus);
        FindBinaryOperators(Focus);
            while(BOFound){
                Process(Focus, Processor);
                //System.out.println("Focus is "+Focus+" length "+Focus.length());
                //System.out.println("Processing Focus... Focus = "+Focus);
            }
                for(temp = 0; temp < Focus.length(); temp++){if(isParentheses(Focus.charAt(temp))) Focus.delete(temp, temp+1);}
                Editor.insert(FocusLeft, Focus);
                Focus.delete(0, Focus.length());
                System.out.println("Now the Editor is "+Editor);
    }
        /*打印是否为第一级二元运算符
        for(index = 0; index<Editor.length(); index++) {
            System.out.println(ptBoolean(isFirstBO(Editor.charAt(index))));
        }*/

        /*打印是否为数字元素
        for(index = 0; index<Editor.length(); index++) {
            System.out.println(ptBoolean(isNumber(Editor.charAt(index))));
        }*/
    public static void CalculateInit () {
        Scanner Inputer = new Scanner(System.in);
        String Formula = Inputer.nextLine();
        StringBuffer Processor = new StringBuffer("");
        StringBuffer Processor2 = new StringBuffer("");
        StringBuffer Focus = new StringBuffer("");
        StringBuffer Editor = new StringBuffer(Formula);

        Inputer.close();
        if(CanBeHandled(Editor)){
        FindFocus(Editor);

        do{
        FindFocus(Editor);
        TransferFocus(Editor, Focus);
        FindBinaryOperators(Focus);
            while(BOFound){
                Process(Focus, Processor);
                //System.out.println("Focus is "+Focus+" length "+Focus.length());
                //System.out.println("Processing Focus... Focus = "+Focus);
            }
                for(temp = 0; temp < Focus.length(); temp++){if(isParentheses(Focus.charAt(temp))) Focus.delete(temp, temp+1);}
                Editor.insert(FocusLeft, Focus);
                Focus.delete(0, Focus.length());
                System.out.println("Next step is "+Editor);
        } while (FocusFound);
        System.out.println("The result is "+Editor);
    }
        /* Pattern isBinaryOperator = Pattern.compile("([+]|[-]|[*]|[/]|[=]){0}([+]|[-]|[*]|[/]){1}([+]|[-])?([+]|[-]|[*]|[/]|[=]){0}");
        Pattern innerParenthese = Pattern.compile("[(][^()]*[)]");
        Pattern isInteger = Pattern.compile("([^.0123456789])\\d+.{0}");
        Pattern isReal = Pattern.compile("");
        Matcher BOT = isBinaryOperator.matcher(Formula);
        Matcher PTT = innerParenthese.matcher(Formula);
        Matcher ITT = isInteger.matcher(Formula);

        System.out.println("The input is: "+Formula);
        while(PTT.find()){
            System.out.println("Found inner parentheses from "+(PTT.start()+1)+" to "+PTT.end()+".");}
        while(BOT.find()){
            System.out.println("Found a binary operator from "+(BOT.start()+1)+" to "+BOT.end()+".");}
        while(ITT.find()){
            System.out.println("Found an Integer from "+(ITT.start()+1)+" to "+ITT.end()+".");} */ //测试：通过正则表达式识别，较难
        
    }


    public static void main(String[] argv) {
        CalculateInit();
    }
}

