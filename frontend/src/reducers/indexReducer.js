import { FETCH_INDEX } from '../actions/types';

const INTIAL_STATE = [];

export default (state = INTIAL_STATE, action) => {
    switch(action.type) {
        case FETCH_INDEX:
            return action.payload;
        default:
            return state;
    }
};