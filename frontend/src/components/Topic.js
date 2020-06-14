import React, { Fragment } from 'react';
import { connect } from 'react-redux';

import { fetchTopic } from '../actions';
import TopicCard from './TopicCard';
import DocumentCard from './DocumentCard';
import RouteBar from './RouteBar';

class Topic extends React.Component {
    componentDidMount() {
        this.props.fetchTopic(this.props.match.params.id);
    }

    componentDidUpdate(oldProps) {
        if (this.props.match.params.id !== oldProps.match.params.id) {
            this.props.fetchTopic(this.props.match.params.id);
        }
    }

    renderList() {
        if (this.props.topic.type === 'root') {
            return this.props.topic.children.map(topic => {
                return <TopicCard key={topic.id} topic={topic} />
            });
        } else {
            return <div className="ui card">
                <div className="content">
                    <div className="description">
                        {this.props.topic.entries.map(entry => {
                            const entryId = `${this.props.topic.id}_${entry.surah}_${entry.ayah}`;
                            return <DocumentCard key={entryId} _id={entryId} parentId={this.props.topic.id} entry={entry} />
                        })}
                    </div>
                </div>
            </div>
        }
    }

    render() {
        if (!this.props.topic) {
            return <div>Loading...</div>;
        }
        return <Fragment>
            <RouteBar topicId={this.props.topic.id} />
            {this.renderList()}
        </Fragment>;
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        topic: state.topic[ownProps.match.params.id]
    };
};

export default connect(
    mapStateToProps,
    { fetchTopic }
)(Topic);
