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
import Filter from "./FilterIcon";
import React from "react";
import { Add, Delete } from "@material-ui/icons";
import validate from "./validate";
import useStyles from "./ScheduleConfigurationStyles";

export default function ExcludeList(props: {
	exclude: { id: string; text: string }[];
	setExclude: any;
}) {
	const classes = useStyles();
	return (
		<List
			subheader={
				<ListSubheader className={classes.listSubheader}>
					Exclude events which contain the following phrases
					<Button
						variant="outlined"
						startIcon={<Add />}
						onClick={(_) =>
							props.setExclude([...props.exclude, { id: uuid(), text: "" }])
						}
					>
						Add
					</Button>
				</ListSubheader>
			}
		>
			{props.exclude.map((excludeItem, index) => (
				<React.Fragment key={excludeItem.id}>
					<ListItem>
						<ListItemIcon>
							<Filter />
						</ListItemIcon>
						<TextField
							margin="dense"
							variant="outlined"
							value={excludeItem.text}
							error={!validate.excludeText(excludeItem.text)}
							label="Text"
							fullWidth
							className={classes.listInput}
							onChange={(e) => {
								const copy = [...props.exclude];
								copy[index].text = e.target.value;
								props.setExclude(copy);
							}}
						></TextField>
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
