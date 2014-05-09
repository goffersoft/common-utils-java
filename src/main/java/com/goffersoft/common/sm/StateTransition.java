/**
 ** File: StateTransition.java
 **
 ** Description : StateTransition class - State Transition common code
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author peaswa000
 *
 */
public class StateTransition<TState extends Trigger<TState>>
{
	private static final Logger log = Logger.getLogger(StateTransition.class);
	
	private State<TState> fromState;
	private ArrayList<State<TState>> toStates;
	
	public StateTransition(State<TState> fromState, ArrayList<State<TState>> toStates)
	{
		this.fromState = fromState;
		this.toStates = toStates;
	}
	
	public StateTransition(StateTransition<TState> s)
	{
		fromState = s.fromState;
		toStates = s.toStates;
	}
	
	public State<TState> getFromState()
	{
		return fromState;
	}
	
	public ArrayList<State<TState>> getToStates()
	{
		return toStates;
	}
	
	public State<TState> getNextState(State<TState> toState) {
		if(toStates == null) {
			return null;
		}
		for(int i = 0; i < toStates.size(); i++) {
			if(toState.getState() == toStates.get(i).getState()) {
				return toStates.get(i);
			}
		}
		return null;
	}
	
	public State<TState> getNextState(TState toState) {
		if(toStates == null) {
			return null;
		}
		for(int i = 0; i < toStates.size(); i++) {
			if(toState == toStates.get(i).getState()) {
				return toStates.get(i);
			}
		}
		return null;
	}
	
	public boolean isValidStateTransition(State<TState> toState) {
		if(toStates == null) {
			return false;
		}
		for(int i = 0; i < toStates.size(); i++) {
			if(toState.getState() == toStates.get(i).getState()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidStateTransition(TState toState) {
		if(toStates == null) {
			return false;
		}
		for(int i = 0; i < toStates.size(); i++) {
			if(toState == toStates.get(i).getState()) {
				return true;
			}
		}
		return false;
	}
}