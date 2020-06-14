import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import style from './TopicCard.module.css';

class TopicCard extends React.Component {
    renderParents() {
        const propName = this.props.language === 'ar' ? 'arParentsNames' : 'enParentsNames';
        let ret = "";
        this.props.topic[propName].forEach(parent => {
            ret = ret + " / " + parent;
        });
        return ret;
    }

    renderName() {
        const propName = this.props.language === 'ar' ? 'arName' : 'enName';
        return this.props.topic[propName];
    }

    renderChildren() {
        const propName = this.props.language === 'ar' ? 'arChildrenNames' : 'enChildrenNames';
        return this.props.topic[propName].map((parent, index) => {
            if (index === 5) {
            return <div key={index} className={`ui label ${style.topicCardChild}`}>...{this.props.topic[propName].length}</div>
            }
            if (index > 5) {
                return null;
            }
            return <div key={index} className={`ui label ${style.topicCardChild}`}>
                {parent}
            </div>;
        });
    }

    render() {
        return (
            <Link to={`/topic/${this.props.topic.id}`} className="ui card">
                <div className="content">
                    {this.props.shouldRenderParents ? <div className={`meta ${style.topicCardMeta}`}>{this.renderParents()}</div> : ''}
                    <div className="header">{this.renderName()}</div>
                    <div className="description">{this.renderChildren()}</div>
                </div>
            </Link>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        language: state.language
    };
};

export default connect(
    mapStateToProps,
    {}
)(TopicCard);