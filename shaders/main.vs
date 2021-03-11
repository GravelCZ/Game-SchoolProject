#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex_coord;

out vec2 TexCoord;

uniform mat4 projection;
uniform mat4 position;

void main()
{
	gl_Position = projection * position * vec4(pos, 1.0);
	TexCoord = tex_coord;
}