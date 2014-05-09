/**
 ** File: State.java
 **
 ** Description : State class - State class associated with a state machine
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/

package com.goffersoft.common.sm;

import org.apache.log4j.Logger;


public class State<TState extends Trigger<TState> > 
{
	private static final Logger log = Logger.getLogger(State.class);
	
	private TState state;
	private TState parentState;
	private TState[] childStates;
	
	public State()
	{
		state = null;
		parentState = null;
		childStates = null;
	}
	
	/**
	 * 
	 * @param s - state definition
	 * @param pState - parentState (null if no parent)
	 * @param cState  - array of child states - null if no child states
	 */
	public State(TState s,
			TState pState,
			TState[] cStates)
	{
		state = s;
		parentState = pState;
		childStates = cStates;
	}
	
	public State(State<TState> s)
	{
		state = s.state;
		parentState = s.parentState;
		childStates = s.childStates;
	}
	
	public TState[] getChildStates()
	{
		return childStates;
	}

	public TState getParentState()
	{
		return parentState;
	}

	public TState getState()
	{
		return state;
	}
	
	@Override
	public Object clone()
	{
		return (Object) new State<TState>(this);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		
		if(obj instanceof State<?>)
		{
			State<?> s = (State<?>)obj;
			
			if(state == s.state &&
				parentState == s.parentState &&
				childStates == s.childStates)
			{
				return true;
			}
		}
		return false;
	}
}
