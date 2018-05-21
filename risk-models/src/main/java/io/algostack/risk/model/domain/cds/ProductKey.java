package io.algostack.risk.model.domain.cds;

import com.google.common.base.Joiner;

import java.io.Serializable;

public class ProductKey implements Serializable {
    private final String name;
    private final ContractualDefinition cDef;
    private final Tier tier;
    private final TransactionType transactionType;

    public ProductKey(String name, ContractualDefinition cDef, Tier tier, TransactionType transactionType) {
        this.name = name;
        this.cDef = cDef;
        this.tier = tier;
        this.transactionType = transactionType;
    }

    public String getName() {
        return name;
    }

    public ContractualDefinition getcDef() {
        return cDef;
    }

    public Tier getTier() {
        return tier;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String toUniqKey() {
        return Joiner.on("#").join(name, cDef, tier, transactionType);
    }

    public static ProductKey fromUniqKey(String key) {
        String[] split = key.split("#");
        return new ProductKey(split[0],
                ContractualDefinition.valueOf(split[1]),
                Tier.valueOf(split[2]),
                TransactionType.valueOf(split[3]));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductKey))
            return false;

        ProductKey that = (ProductKey) o;

        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (cDef != that.cDef)
            return false;
        if (tier != that.tier)
            return false;
        return transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cDef != null ? cDef.hashCode() : 0);
        result = 31 * result + (tier != null ? tier.hashCode() : 0);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductKey{" +
                "name='" + name + '\'' +
                ", cDef=" + cDef +
                ", tier=" + tier +
                ", transactionType=" + transactionType +
                '}';
    }


}