/**
 ** File: ExampleStateMachine.java
 **
 ** Description : ExampleStateMachine class - State Machine 
 **               receives  commands over udp :7777
 **               responds bck to udp 6666
 **               extends the GenericStateMachine class
 ** Date           Author                          Comments
 ** 08/31/2013     Prakash Easwar                  Created  
 **/
package com.goffersoft.common.sm.example;

import java.util.ArrayList;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

import com.goffersoft.common.sm.GenericStateMachine;
import com.goffersoft.common.sm.State;
import com.goffersoft.common.sm.StateTransition;
import com.goffersoft.common.sm.Trigger;

public class ExampleStateMachine extends
        GenericStateMachine<ExampleStateMachine.States> {
    // private ScheduledExecutorService executor =
    // Executors.newSingleThreadScheduledExecutor();

    private static final Logger log = Logger
            .getLogger(ExampleStateMachine.class);

    public static enum States
            implements Trigger<States> {
        INACTIVE,
        ACTIVE,
        PAUSED,
        EXIT;

        public static final States[] stateName = {
                INACTIVE,
                ACTIVE,
                PAUSED,
                EXIT,
        };

        public static final String[] stateNameStr = {
                "Inactive",
                "Active",
                "Paused",
                "Exit",
        };

        public static String getState(States m) {
            return stateNameStr[m.ordinal()];
        }

        public static States getState(String s) {
            int i = 0;

            for (i = 0; i < stateNameStr.length; i++) {
                if (stateNameStr[i].equals(s)) {
                    break;
                }
            }

            if (i == stateNameStr.length) {
                return null;
            }
            return stateName[i];
        }

        public static int length() {
            return stateName.length;
        }

        void doOnEntry_InactiveState(ExampleStateMachine sm,
                State<States> currentState, State<States> fromState,
                Object object)

        {
            log.info("Entering State: " + States.getState(States.INACTIVE));
        }

        void doOnExit_InactiveState(ExampleStateMachine sm,
                State<States> currentState, State<States> toState, Object object) {
            log.info("Exiting State: " + States.getState(States.INACTIVE));
        }

        void doOnEntry_ActiveState(ExampleStateMachine sm,
                State<States> currentState, State<States> fromState,
                Object object) {
            log.info("Entering State: " + States.getState(States.ACTIVE));
        }

        void doOnExit_ActiveState(ExampleStateMachine sm,
                State<States> currentState, State<States> toState, Object object) {
            log.info("Exiting State: " + States.getState(States.ACTIVE));
        }

        void doOnEntry_PausedState(ExampleStateMachine sm,
                State<States> currentState, State<States> fromState,
                Object object) {
            log.info("Entering State: " + States.getState(States.PAUSED));
        }

        void doOnExit_PausedState(ExampleStateMachine sm,
                State<States> currentState, State<States> toState, Object object) {
            log.info("Exiting State: " + States.getState(States.PAUSED));
        }

        void doOnEntry_ExitState(ExampleStateMachine sm,
                State<States> currentState, State<States> fromState,
                Object object) {
            log.info("Entering State: " + States.getState(States.EXIT));
        }

        void doOnExit_ExitState(ExampleStateMachine sm,
                State<States> currentState, State<States> toState, Object object) {
            log.info("Exiting State: " + States.getState(States.EXIT));
        }

        public void fireOnEntry(GenericStateMachine<States> sm,
                State<States> currentState, State<States> fromState,
                Object object) {
            switch (currentState.getState()) {
                case INACTIVE:
                    doOnEntry_InactiveState((ExampleStateMachine) sm,
                            currentState, fromState, object);
                    break;
                case ACTIVE:
                    doOnEntry_ActiveState((ExampleStateMachine) sm,
                            currentState, fromState, object);
                    break;
                case PAUSED:
                    doOnEntry_PausedState((ExampleStateMachine) sm,
                            currentState, fromState, object);
                    break;
                case EXIT:
                    doOnEntry_ExitState((ExampleStateMachine) sm, currentState,
                            fromState, object);
                    break;
                default:
            }
            return;
        }

        public void fireOnExit(GenericStateMachine<States> sm,
                State<States> currentState, State<States> toState, Object object) {
            switch (currentState.getState()) {
                case INACTIVE:
                    doOnExit_InactiveState((ExampleStateMachine) sm,
                            currentState, toState, object);
                    break;
                case ACTIVE:
                    doOnExit_ActiveState((ExampleStateMachine) sm,
                            currentState, toState, object);
                    break;
                case PAUSED:
                    doOnExit_PausedState((ExampleStateMachine) sm,
                            currentState, toState, object);
                    break;
                case EXIT:
                    doOnExit_ExitState((ExampleStateMachine) sm, currentState,
                            toState, object);
                    break;
                default:
            }
            return;
        }
    }

    public static ArrayList<State<States>> states;
    public static ArrayList<StateTransition<States>> stateTransitions;
    private static ArrayList<State<States>> tmp;

    static {
        states = new ArrayList<State<States>>();
        states.add(States.INACTIVE.ordinal(), new State<States>(
                States.INACTIVE, null, null));
        states.add(States.ACTIVE.ordinal(), new State<States>(States.ACTIVE,
                null, null));
        states.add(States.PAUSED.ordinal(), new State<States>(States.PAUSED,
                null, null));
        states.add(States.EXIT.ordinal(), new State<States>(States.EXIT, null,
                null));

        stateTransitions = new ArrayList<StateTransition<States>>();

        tmp = new ArrayList<State<States>>();

        tmp = new ArrayList<State<States>>();
        tmp.add(states.get(States.INACTIVE.ordinal()));
        tmp.add(states.get(States.ACTIVE.ordinal()));
        stateTransitions.add(new StateTransition<States>(null, tmp));

        tmp = new ArrayList<State<States>>();
        tmp.add(states.get(States.INACTIVE.ordinal()));
        tmp.add(states.get(States.ACTIVE.ordinal()));
        tmp.add(states.get(States.EXIT.ordinal()));
        stateTransitions.add(new StateTransition<States>(states
                .get(States.INACTIVE.ordinal()), tmp));

        tmp = new ArrayList<State<States>>();
        tmp.add(states.get(States.INACTIVE.ordinal()));
        tmp.add(states.get(States.ACTIVE.ordinal()));
        tmp.add(states.get(States.PAUSED.ordinal()));
        stateTransitions.add(new StateTransition<States>(states
                .get(States.ACTIVE.ordinal()), tmp));

        tmp = new ArrayList<State<States>>();
        tmp.add(states.get(States.INACTIVE.ordinal()));
        tmp.add(states.get(States.ACTIVE.ordinal()));
        tmp.add(states.get(States.PAUSED.ordinal()));
        stateTransitions.add(new StateTransition<States>(states
                .get(States.PAUSED.ordinal()), tmp));

        tmp = new ArrayList<State<States>>();
        stateTransitions.add(new StateTransition<States>(states.get(States.EXIT
                .ordinal()), tmp));
        tmp = null;
    }

    public ExampleStateMachine() {
        super(states, stateTransitions);
    }
}