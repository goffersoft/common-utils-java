/**
 ** File: Trigger.java
 **
 ** Description : Trigger Interface - The custom defined TState class
 **               passed to State must implement this interface
 **
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm;

/**
 * @author peaswa000
 *
 */
public interface Trigger<TState extends Trigger<TState>>
{
	public void fireOnEntry(GenericStateMachine<TState> sm, 
							State<TState> currentState,
							State<TState> fromState,
							Object object
							);
	
	public void fireOnExit(GenericStateMachine<TState> stateMachine, 
							State<TState> currentState,
							State<TState> toState,
							Object object);
}
