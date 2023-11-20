package compilador;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Semantico implements Constants {
    private String operador;
    public Queue<String> codigo;
    private Stack<String> pilha_tipos;
    private Queue<String> lista_id;
    private Stack<Integer> pilha_rotulos;
    private HashMap<String, String> tabela_identificadores;
    private int rotulo;

    public Semantico() {
        operador = "";
        codigo = new LinkedList<String>();
        pilha_tipos = new Stack<String>();
        lista_id = new LinkedList<String>();
        pilha_rotulos = new Stack<Integer>();
        rotulo = 1;
    }

    public void executeAction(int action, Token token) throws SemanticError {
        System.out.println("Ação #" + action + ", Token: " + token);
        switch (action) {
            case 100:
                acao_100();
                break;
            case 101:
                acao_101();
                break;
            case 102:
                acao_102();
                break;
            case 103:
                acao_103(token);
                break;
            case 104:
                acao_104(token);
                break;
            case 105:
                acao_105();
                break;
            case 106:
                acao_106();
                break;
            case 107:
                acao_107(token);
                break;
            case 108:
                acao_108(token);
                break;
            case 110:
                acao_110(token);
                break;
            case 111:
                acao_111(token);
                break;
            case 112:
                acao_112(token);
                break;
            case 113:
                acao_113(token);
                break;
            case 114:
                acao_114(token);
                break;
            case 115:
                acao_115(token);
                break;
            case 116:
                acao_116(token);
                break;
            case 117:
                acao_117(token);
                break;
        }
    }

    public void acao_100() {
        codigo.add(".assembly extern mscorlib {}\n" +
                ".assembly _exemplo{}\n" +
                ".module _exemplo.exe\n" +
                "\n" +
                ".class public _exemplo{\n" +
                "  .method static public void _principal(){\n" +
                "     .entrypoint");
    }

    public void acao_101() {
        codigo.add("   ret\n" +
                "  }\n" +
                "}\n");
    }

    public void acao_102() {
        String tipo = pilha_tipos.pop();

        if (tipo.equals("int64"))
            codigo.add("conv.i8\n");

        codigo.add("call void [mscorlib]System.Console::Write(" + tipo + ")\n");
    }

    public void acao_103(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if (tipo1.equals("bool") && tipo1.equals(tipo2)) {
            pilha_tipos.push("bool");
            codigo.add("and\n");
        } else {
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o l�gica", token.getPosition());
        }
    }

    public void acao_104(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if (tipo1.equals("bool") && tipo1.equals(tipo2)) {
            pilha_tipos.push("bool");
            codigo.add("or\n");
        } else {
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o l�gica", token.getPosition());
        }
    }

    public void acao_105() {
        pilha_tipos.push("bool");
        codigo.add("ldc.i4.1\n");
    }

    public void acao_106() {
        pilha_tipos.push("bool");
        codigo.add("ldc.i4.0\n");
    }

    public void acao_107(Token token) throws SemanticError {
        String tipo = pilha_tipos.pop();
        if (tipo.equals("bool")) {
            pilha_tipos.push("bool");
        } else {
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o l�gica", token.getPosition());
        }

        codigo.add("ldc.i4.1\n");
        codigo.add("xor\n");
    }

    public void acao_108(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if (tipo1.equals(tipo2))
            pilha_tipos.push("bool");
        else {
            throw new SemanticError("tipos incompat�veis em express�o relacional", token.getPosition());
        }

        switch (operador) {
            case ">":
                codigo.add("cgt\n");
                break;
            case "<":
                codigo.add("clt\n");
                break;
            case "==":
                codigo.add("ceq\n");
                break;
            case "<>":
                codigo.add("ceq\n");
                codigo.add("ldc.i4.0\n");
                codigo.add("ceq\n");
                break;
        }
    }

    public void acao_110(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();
        if ((!tipo1.equals("float64") && !tipo1.equals("int64"))
                || (!tipo2.equals("float64") && !tipo2.equals("int64")))
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o aritm�tica", token.getPosition());
        else {
            if (tipo1.equals("float64") || tipo2.equals("float64"))
                pilha_tipos.push("float64");
            else
                pilha_tipos.push("int64");

            codigo.add("add\n");
        }
    }

    public void acao_111(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if ((!tipo1.equals("float64") && !tipo1.equals("int64"))
                || (!tipo2.equals("float64") && !tipo2.equals("int64")))
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o aritm�tica", token.getPosition());
        else {
            if (tipo1.equals("float64") || tipo2.equals("float64"))
                pilha_tipos.push("float64");
            else
                pilha_tipos.push("int64");

            codigo.add("sub\n");
        }
    }

    public void acao_112(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if ((!tipo1.equals("float64") && !tipo1.equals("int64"))
                || (!tipo2.equals("float64") && !tipo2.equals("int64")))
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o aritm�tica", token.getPosition());
        else {
            if (tipo1.equals("float64") || tipo2.equals("float64"))
                pilha_tipos.push("float64");
            else
                pilha_tipos.push("int64");

            codigo.add("mul\n");
        }
    }

    public void acao_113(Token token) throws SemanticError {
        String tipo1 = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();

        if ((!tipo1.equals("float64") && !tipo1.equals("int64"))
                || (!tipo2.equals("float64") && !tipo2.equals("int64")))
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o aritm�tica", token.getPosition());
        else {
            pilha_tipos.push("float64");
            codigo.add("div\n");
        }
    }

    public void acao_114(Token token) {
        pilha_tipos.push("int64");
        codigo.add("ldc.i8 " + token.getLexeme() + "\n");
        codigo.add("conv.r8\n");
    }

    public void acao_115(Token token) {
        pilha_tipos.push("float64");
        codigo.add("ldc.r8 " + token.getLexeme() + "\n");
    }

    public void acao_116(Token token) {
        pilha_tipos.push("string");
        codigo.add("ldstr " + token.getLexeme() + "\n");
    }

    public void acao_117(Token token) throws SemanticError {
        String tipo = pilha_tipos.pop();

        if (tipo.equals("float64") || tipo.equals("int64"))
            pilha_tipos.push(tipo);
        else {
            throw new SemanticError("tipo(s) incompat�vel(is) em express�o aritm�tica", token.getPosition());
        }

        codigo.add("ldc.i8 -1\n");
        codigo.add("conv.r8\n");
        codigo.add("mul\n");
    }
}
