package edu.networking.exercise4;



public  class Calculator {
    private String func;
    private boolean useRadians = true;

    public Calculator(String function) {
        changeFunction(function);
    }

    public void changeFunction(String function) {
        switch (function) {
            case "fun:sin": func = "sin"; break;
            case "fun:cos": func = "cos"; break;
            case "fun:tan": func = "tan"; break;
            default: throw new IllegalArgumentException("Función no válida: " + function);
        }
    }

    public void useRadians(boolean useRadians) {
        this.useRadians = useRadians;
    }

    public double function(double number) {
        double rad = useRadians ? number : Math.toRadians(number);

        switch (func) {
            case "sin": return Math.sin(rad);
            case "cos": return Math.cos(rad);
            case "tan": return Math.tan(rad);
            default: throw new IllegalStateException("Función no configurada");
        }
    }

    public double interpretarExpresion(String entrada) {
        String expr = entrada.replace(" ", "").toLowerCase();

        boolean enGrados = expr.contains("°");
        expr = expr.replace("°", "");

        if (expr.equals("π") || expr.equals("pi")) {
            return Math.PI;
        }
        if (expr.equals("e")) {
            return Math.E;
        }

        if (expr.contains("/")) {
            String[] partes = expr.split("/");
            if (partes.length == 2) {
                double numerador = parsearParte(partes[0]);
                double denominador = parsearParte(partes[1]);
                return numerador / denominador;
            }
        }
        if (expr.contains("π") || expr.contains("pi")) {
            String piPattern = expr.contains("π") ? "π" : "pi";
            expr = expr.replace(piPattern, "");

            double factor;
            if (expr.isEmpty()) {
                factor = 1;
            } else if (expr.equals("-")) {
                factor = -1;
            } else {
                factor = Double.parseDouble(expr);
            }
            return factor * Math.PI;
        }
        double valor = Double.parseDouble(expr);
        return enGrados ? Math.toRadians(valor) : valor;
    }

    private double parsearParte(String parte) {
        if (parte.isEmpty()) return 1;
        if (parte.equals("π") || parte.equals("pi")) return Math.PI;
        if (parte.equals("-π") || parte.equals("-pi")) return -Math.PI;

        if (parte.contains("π") || parte.contains("pi")) {
            String piPattern = parte.contains("π") ? "π" : "pi";
            String coef = parte.replace(piPattern, "");
            double factor = coef.isEmpty() ? 1 : (coef.equals("-") ? -1 : Double.parseDouble(coef));
            return factor * Math.PI;
        }

        return Double.parseDouble(parte);
    }
}