import { SET_LANGUAGE } from '../actions/types';

const INIT_STATE = "en";

export default (state = INIT_STATE, action) => {
    switch (action.type) {
        case SET_LANGUAGE:
            return action.payload;
        default:
            return state;
    }
};
