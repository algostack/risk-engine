package io.algostack.risk.model.domain.cds;

import java.io.Serializable;
import java.time.LocalDate;

public class PriceArrayKey implements Serializable {

    private final ProductKey productKey;
    private final int coupon;
    private final LocalDate maturity;

    public PriceArrayKey(ProductKey productKey, int coupon, LocalDate maturity) {
        this.productKey = productKey;
        this.coupon = coupon;
        this.maturity = maturity;
    }

    public ProductKey getProductKey() {
        return productKey;
    }

    public int getCoupon() {
        return coupon;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PriceArrayKey))
            return false;

        PriceArrayKey that = (PriceArrayKey) o;

        if (coupon != that.coupon)
            return false;
        if (productKey != null ? !productKey.equals(that.productKey) : that.productKey != null)
            return false;
        return maturity != null ? maturity.equals(that.maturity) : that.maturity == null;
    }

    @Override
    public int hashCode() {
        int result = productKey != null ? productKey.hashCode() : 0;
        result = 31 * result + coupon;
        result = 31 * result + (maturity != null ? maturity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PriceArrayKey{" +
                "productKey=" + productKey +
                ", coupon=" + coupon +
                ", maturity=" + maturity +
                '}';
    }
}
