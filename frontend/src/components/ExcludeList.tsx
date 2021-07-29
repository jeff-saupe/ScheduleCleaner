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
import Filter from "../styles/FilterIcon";
import React from "react";
import { Add, Delete } from "@material-ui/icons";
//import validate from "../utils/Validate";
import useStyles from "../styles/ConfiguratorStyles";

export default function ExcludeList(props: {
	exclude: { id: string; text: string }[];
	setExclude: any;
}) {
	const classes = useStyles();
	return (
		<List
			subheader={
				<ListSubheader className={classes.listSubheader}>
					<ListItemIcon>
						<Filter />
					</ListItemIcon>
					<ListItemText className={classes.listItemText}>
						Exclude events
					</ListItemText>
					<Tooltip title="Exclude specific events based on phrases it must ALL contain" arrow>
						<Button
							variant="outlined"
							startIcon={<Add />}
							onClick={(_) =>
								props.setExclude([...props.exclude, { id: uuid(), text: "" }])
							}
						>
							Add
						</Button>
					</Tooltip>
				</ListSubheader>
			}
		>
			{props.exclude.map((excludeItem, index) => (
				<React.Fragment key={excludeItem.id}>
					<ListItem>
						<Tooltip title="Phrases can be separated by using ; (semicolon)" arrow>
							<TextField
								margin="dense"
								variant="outlined"
								value={excludeItem.text}
								//error={!validate.excludeText(excludeItem.text)}
								label="Text"
								fullWidth
								className={classes.listInput}
								onChange={(e) => {
									const copy = [...props.exclude];
									copy[index].text = e.target.value;
									props.setExclude(copy);
								}}
							></TextField>
						</Tooltip>
						<ListItemSecondaryAction>
							<IconButton
								edge="end"
								onClick={(e) => {
									const copy = [...props.exclude];
									copy.splice(index, 1);
									props.setExclude(copy);
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
