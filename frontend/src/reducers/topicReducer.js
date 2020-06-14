import { FETCH_TOPIC } from '../actions/types';

const INIT_STATE = {
};

export default (state = INIT_STATE, action) => {
    switch(action.type) {
        case FETCH_TOPIC:
            return { ...state, [action.payload.id]: action.payload};
        default:
            return state;
    }
};
