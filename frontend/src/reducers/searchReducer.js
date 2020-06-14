import { DO_SEARCH } from '../actions/types';

const INIT_STATE = {
};

export default (state = INIT_STATE, action) => {
    switch(action.type) {
        case DO_SEARCH:
            return { ...state, [action.payload.query]: action.payload.result};
        default:
            return state;
    }
};
