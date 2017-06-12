package com.mapua.aquajmt.customerapp.models;

import java.util.Date;

/**
 * Created by Bryan on 6/12/2017.
 */

public class ShopSalesInfo {

    private String id;
    private boolean roundOffered;
    private boolean slimOffered;

    private int roundStock;
    private int slimStock;

    private boolean distilledAvailable;
    private boolean purifiedAvailable;
    private boolean mineralAvailable;
    private boolean alkalineAvailable;

    private double distilledPrice;
    private double purifiedPrice;
    private double mineralPrice;
    private double alkalinePrice;

    private double slimContainerCost;
    private double roundContainerCost;

    private Date createdOn;
    private Date updatedOn;
    private String updatedBy;

    public ShopSalesInfo(String id,
                         boolean roundOffered,
                         boolean slimOffered,
                         int roundStock,
                         int slimStock,
                         boolean distilledAvailable,
                         boolean purifiedAvailable,
                         boolean mineralAvailable,
                         boolean alkalineAvailable,
                         double distilledPrice,
                         double purifiedPrice,
                         double mineralPrice,
                         double alkalinePrice,
                         double slimContainerCost,
                         double roundContainerCost,
                         Date createdOn,
                         Date updatedOn,
                         String updatedBy) {

        if (id == null || createdOn == null || updatedOn == null || updatedBy == null) {
            throw new IllegalArgumentException("Properties of this object is not allowed " +
                    "to contain null values.");
        }

        this.id = id;
        this.roundOffered = roundOffered;
        this.slimOffered = slimOffered;
        this.roundStock = roundStock;
        this.slimStock = slimStock;
        this.distilledAvailable = distilledAvailable;
        this.purifiedAvailable = purifiedAvailable;
        this.mineralAvailable = mineralAvailable;
        this.alkalineAvailable = alkalineAvailable;
        this.distilledPrice = distilledPrice;
        this.purifiedPrice = purifiedPrice;
        this.mineralPrice = mineralPrice;
        this.alkalinePrice = alkalinePrice;
        this.slimContainerCost = slimContainerCost;
        this.roundContainerCost = roundContainerCost;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRoundOffered() {
        return roundOffered;
    }

    public void setRoundOffered(boolean roundOffered) {
        this.roundOffered = roundOffered;
    }

    public boolean isSlimOffered() {
        return slimOffered;
    }

    public void setSlimOffered(boolean slimOffered) {
        this.slimOffered = slimOffered;
    }

    public int getRoundStock() {
        return roundStock;
    }

    public void setRoundStock(int roundStock) {
        this.roundStock = roundStock;
    }

    public int getSlimStock() {
        return slimStock;
    }

    public void setSlimStock(int slimStock) {
        this.slimStock = slimStock;
    }

    public boolean isDistilledAvailable() {
        return distilledAvailable;
    }

    public void setDistilledAvailable(boolean distilledAvailable) {
        this.distilledAvailable = distilledAvailable;
    }

    public boolean isPurifiedAvailable() {
        return purifiedAvailable;
    }

    public void setPurifiedAvailable(boolean purifiedAvailable) {
        this.purifiedAvailable = purifiedAvailable;
    }

    public boolean isMineralAvailable() {
        return mineralAvailable;
    }

    public void setMineralAvailable(boolean mineralAvailable) {
        this.mineralAvailable = mineralAvailable;
    }

    public boolean isAlkalineAvailable() {
        return alkalineAvailable;
    }

    public void setAlkalineAvailable(boolean alkalineAvailable) {
        this.alkalineAvailable = alkalineAvailable;
    }

    public double getDistilledPrice() {
        return distilledPrice;
    }

    public void setDistilledPrice(double distilledPrice) {
        this.distilledPrice = distilledPrice;
    }

    public double getPurifiedPrice() {
        return purifiedPrice;
    }

    public void setPurifiedPrice(double purifiedPrice) {
        this.purifiedPrice = purifiedPrice;
    }

    public double getMineralPrice() {
        return mineralPrice;
    }

    public void setMineralPrice(double mineralPrice) {
        this.mineralPrice = mineralPrice;
    }

    public double getAlkalinePrice() {
        return alkalinePrice;
    }

    public void setAlkalinePrice(double alkalinePrice) {
        this.alkalinePrice = alkalinePrice;
    }

    public double getSlimContainerCost() {
        return slimContainerCost;
    }

    public void setSlimContainerCost(double slimContainerCost) {
        this.slimContainerCost = slimContainerCost;
    }

    public double getRoundContainerCost() {
        return roundContainerCost;
    }

    public void setRoundContainerCost(double roundContainerCost) {
        this.roundContainerCost = roundContainerCost;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
