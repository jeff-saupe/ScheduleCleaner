import {
	Button,
	IconButton,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListSubheader,
	ListItemText,
	TextField,
	Tooltip
} from "@material-ui/core";
import { v4 as uuid } from "uuid";
import validate from "../utils/Validate";
import React from "react";
import { Add, Delete, Edit } from "@material-ui/icons";
import useStyles from "../styles/ConfiguratorStyles";

export default function ReplaceList(props: {
	replace: { before: string; after: string; id: string }[];
	setReplace: any;
}) {
	const classes = useStyles();
	return (
		<List
			subheader={
				<ListSubheader className={classes.listSubheader}>
					<ListItemIcon>
						<Edit />
					</ListItemIcon>
					<ListItemText className={classes.listItemText}>
						Replace text
					</ListItemText>
					<Tooltip title="Replace the text of an event with another text" arrow>
						<Button
							variant="outlined"
							startIcon={<Add />}
							onClick={(_) =>
								props.setReplace([
									...props.replace,
									{ before: "", after: "", id: uuid() },
								])
							}
						>
							Add
						</Button>
					</Tooltip>
				</ListSubheader>
			}
		>
			{props.replace.map((replaceItem, index) => (
				<React.Fragment key={replaceItem.id}>
					<ListItem>
						<TextField
							margin="dense"
							variant="outlined"
							label="Before"
							error={!validate.replaceText(replaceItem.before)}
							onChange={(e) => {
								const copy = [...props.replace];
								copy[index].before = e.target.value;
								props.setReplace(copy);
							}}
						>
							{replaceItem.before}
						</TextField>
						<div style={{ marginRight: 10 }}></div>
						<TextField
							margin="dense"
							variant="outlined"
							label="After"
							className={classes.listInput}
							error={!validate.replaceText(replaceItem.after)}
							onChange={(e) => {
								const copy = [...props.replace];
								copy[index].after = e.target.value;
								props.setReplace(copy);
							}}
						>
							{replaceItem.after}
						</TextField>

						<ListItemSecondaryAction>
							<IconButton
								edge="end"
								onClick={(e) => {
									const copy = [...props.replace];
									copy.splice(index, 1);
									props.setReplace(copy);
								}}
							>
								<Delete />
							</IconButton>
						</ListItemSecondaryAction>
					</ListItem>
				</React.Fragment>
			))}
		</List>
	);
}
