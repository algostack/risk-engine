package io.algostack.risk.model.domain.cds;

public interface KeyExtractor<I,O> {

    KeyExtractor<PriceArrayKey, ProductKey> PRICE_ARRAY_TO_PRODUCT_KEY = (KeyExtractor<PriceArrayKey, ProductKey>) input -> input.getProductKey();

    O extract(I input);
}
