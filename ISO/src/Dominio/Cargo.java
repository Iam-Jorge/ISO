package Dominio;

public enum Cargo {
	conserjería("Conserjería"),
	limpieza("Limpieza"),
	administración("Administración");
	
	private String valor;
	
	Cargo(String nuevoValor){
        this.valor = nuevoValor;
    }
	
	public static Cargo getCargo(String valor) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.valor.equalsIgnoreCase(valor)) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("No existe el cargo " + valor);
    }
}
