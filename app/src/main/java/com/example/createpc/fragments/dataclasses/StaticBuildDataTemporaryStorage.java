package com.example.createpc.fragments.dataclasses;

import java.util.ArrayList;
import java.util.List;

public final class StaticBuildDataTemporaryStorage {

    private static boolean isEmpty = true;
    private static PcCardData cpuCard;
    private static PcCardData gpuCard;
    private static PcCardData motherboardCard;
    private static PcCardData psuCard;
    private static PcCardData ramCard;
    private static PcCardData cpuCoolingCard;
    private static PcCardData caseCard;
    private static PcCardData ssdMCard;
    private static PcCardData ssd2Card;
    private static PcCardData hddCard;
    private static PcCardData caseCoolingCard;

    private StaticBuildDataTemporaryStorage() {
        StaticBuildDataTemporaryStorage.setIsEmpty(true);
    }

    public static void clearAll() {
        StaticBuildDataTemporaryStorage.setIsEmpty(true);
        List<PcCardData> list = StaticBuildDataTemporaryStorage.getCardsList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDefaultValues();
        }
    }

    public static void setAllCards(List<PcCardData> list) {
        StaticBuildDataTemporaryStorage.setIsEmpty(false);
        setCpuCard(list.get(0));
        setGpuCard(list.get(1));
        setMotherboardCard(list.get(2));
        setPsuCard(list.get(3));
        setRamCard(list.get(4));
        setCpuCoolingCard(list.get(5));
        setCaseCard(list.get(6));
        setSsdMCard(list.get(7));
        setSsd2Card(list.get(8));
        setHddCard(list.get(9));
        setCaseCoolingCard(list.get(10));
    }

    public static List<PcCardData> getCardsList() {
        List<PcCardData> list = new ArrayList<>();
        list.add(getCpuCard());
        list.add(getGpuCard());
        list.add(getMotherboardCard());
        list.add(getPsuCard());
        list.add(getRamCard());
        list.add(getCpuCoolingCard());
        list.add(getCaseCard());
        list.add(getSsdMCard());
        list.add(getSsd2Card());
        list.add(getHddCard());
        list.add(getCaseCoolingCard());
        return list;
    }

    public static boolean isIsEmpty() {
        return isEmpty;
    }

    public static void setIsEmpty(boolean isEmpty) {
        StaticBuildDataTemporaryStorage.isEmpty = isEmpty;
    }

    public static PcCardData getCpuCard() {
        return cpuCard;
    }

    public static void setCpuCard(PcCardData cpuCard) {
        StaticBuildDataTemporaryStorage.cpuCard = cpuCard;
    }

    public static PcCardData getGpuCard() {
        return gpuCard;
    }

    public static void setGpuCard(PcCardData gpuCard) {
        StaticBuildDataTemporaryStorage.gpuCard = gpuCard;
    }

    public static PcCardData getMotherboardCard() {
        return motherboardCard;
    }

    public static void setMotherboardCard(PcCardData motherboardCard) {
        StaticBuildDataTemporaryStorage.motherboardCard = motherboardCard;
    }

    public static PcCardData getPsuCard() {
        return psuCard;
    }

    public static void setPsuCard(PcCardData psuCard) {
        StaticBuildDataTemporaryStorage.psuCard = psuCard;
    }

    public static PcCardData getRamCard() {
        return ramCard;
    }

    public static void setRamCard(PcCardData ramCard) {
        StaticBuildDataTemporaryStorage.ramCard = ramCard;
    }

    public static PcCardData getCpuCoolingCard() {
        return cpuCoolingCard;
    }

    public static void setCpuCoolingCard(PcCardData cpuCoolingCard) {
        StaticBuildDataTemporaryStorage.cpuCoolingCard = cpuCoolingCard;
    }

    public static PcCardData getCaseCard() {
        return caseCard;
    }

    public static void setCaseCard(PcCardData caseCard) {
        StaticBuildDataTemporaryStorage.caseCard = caseCard;
    }

    public static PcCardData getSsdMCard() {
        return ssdMCard;
    }

    public static void setSsdMCard(PcCardData ssdMCard) {
        StaticBuildDataTemporaryStorage.ssdMCard = ssdMCard;
    }

    public static PcCardData getSsd2Card() {
        return ssd2Card;
    }

    public static void setSsd2Card(PcCardData ssd2Card) {
        StaticBuildDataTemporaryStorage.ssd2Card = ssd2Card;
    }

    public static PcCardData getHddCard() {
        return hddCard;
    }

    public static void setHddCard(PcCardData hddCard) {
        StaticBuildDataTemporaryStorage.hddCard = hddCard;
    }

    public static PcCardData getCaseCoolingCard() {
        return caseCoolingCard;
    }

    public static void setCaseCoolingCard(PcCardData caseCoolingCard) {
        StaticBuildDataTemporaryStorage.caseCoolingCard = caseCoolingCard;
    }
}
