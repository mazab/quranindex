import React, { Fragment } from 'react';
import { connect } from 'react-redux';

import { fetchIndex } from '../actions';
import TopicCard from './TopicCard';

class Index extends React.Component {
    componentDidMount() {
        this.props.fetchIndex();
    }

    renderList() {
        return this.props.index.map(topic => {
            return <TopicCard key={topic.id} topic={topic} />
        });
    }

    render() {
        if (!this.props.index) {
            return <div className="ui loading card">
                <div className="content">
                    <div className="description">Loading...</div>
                </div>
            </div>;
        }
        return <Fragment>{this.renderList()}</Fragment>;
    }
}

const mapStateToProps = state => {
    return {
        index: state.index
    };
};

export default connect(
    mapStateToProps,
    { fetchIndex }
)(Index);
