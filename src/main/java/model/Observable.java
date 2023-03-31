package model;

public abstract class Observable {
    public abstract void addObserver(Observer observer);
    public abstract void notifyObservers();
}