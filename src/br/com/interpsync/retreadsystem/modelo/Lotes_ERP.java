/*  1:   */ package br.com.interpsync.retreadsystem.modelo;

/*  3:   */ public class Lotes_ERP
/*  4:   */ {
/*  5:   */   private Integer NrLote;
/*  6:   */   private String ClienteID;
              private String VendedorID;
              private boolean fechado;
/*  7:   */   
/*  8:   */   public Integer getNrLote()
/*  9:   */   {
/* 10:16 */     return this.NrLote;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void setNrLote(Integer NrLote)
/* 14:   */   {
/* 15:20 */     this.NrLote = NrLote;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getClienteID()
/* 19:   */   {
/* 20:24 */     return this.ClienteID;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setClienteID(String ClienteID)
/* 24:   */   {
/* 25:28 */     this.ClienteID = ClienteID;
/* 26:   */   }
/* 27:   */

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    public String getVendedorID() {
        return VendedorID;
    }

    public void setVendedorID(String VendedorID) {
        this.VendedorID = VendedorID;
    }
    
    
 }



/* Location:           C:\Users\Leonardo\Desktop\INT\InterpSync.jar

 * Qualified Name:     br.com.interpsync.retreadsystem.modelo.Lotes_ERP

 * JD-Core Version:    0.7.0.1

 */