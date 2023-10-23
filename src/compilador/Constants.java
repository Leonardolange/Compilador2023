package compilador;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_id = 2;
    int t_int = 3;
    int t_float = 4;
    int t_string = 5;
    int t_palavra = 6;
    int t_pr_do = 7;
    int t_pr_else = 8;
    int t_pr_false = 9;
    int t_pr_fun = 10;
    int t_pr_if = 11;
    int t_pr_in = 12;
    int t_pr_main = 13;
    int t_pr_out = 14;
    int t_pr_repeat = 15;
    int t_pr_true = 16;
    int t_pr_while = 17;
    int t_and = 18;
    int t_or = 19;
    int t_not = 20;
    int t_comma = 21;
    int t_semicolon = 22;
    int t_equal = 23;
    int t_colon = 24;
    int t_parenthesis_o = 25;
    int t_parenthesis_c = 26;
    int t_brace_o = 27;
    int t_brace_c = 28;
    int t_equity = 29;
    int t_different = 30;
    int t_minor = 31;
    int t_major = 32;
    int t_plus = 33;
    int t_minus = 34;
    int t_multiply = 35;
    int t_split = 36;

}
