import { FETCH_DOCUMENT } from '../actions/types';

const INTIAL_STATE = {
};

export default (state = INTIAL_STATE, action) => {
    switch (action.type) {
        case FETCH_DOCUMENT:
            return { ...state, [action.payload.documentId]: action.payload.document };
        default:
            return state;
    }
};
