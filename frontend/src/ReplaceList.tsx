import {
	Button,
	IconButton,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListSubheader,
	TextField,
} from "@material-ui/core";
import { v4 as uuid } from "uuid";
import validate from "./validate";
import React from "react";
import { Add, Delete, Edit } from "@material-ui/icons";
import useStyles from "./ScheduleConfigurationStyles";

export default function ReplaceList(props: {
	replace: { before: string; after: string; id: string }[];
	setReplace: any;
}) {
	const classes = useStyles();
	return (
		<List
			subheader={
				<ListSubheader className={classes.listSubheader}>
					Replace a text
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
				</ListSubheader>
			}
		>
			{props.replace.map((replaceItem, index) => (
				<React.Fragment key={replaceItem.id}>
					<ListItem>
						<ListItemIcon>
							<Edit />
						</ListItemIcon>

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
